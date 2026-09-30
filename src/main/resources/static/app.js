const form = document.querySelector("#employee-form");
const rows = document.querySelector("#rows");
const empty = document.querySelector("#empty");
const message = document.querySelector("#message");
const formTitle = document.querySelector("#form-title");
const formHint = document.querySelector("#form-hint");
const saveButton = document.querySelector("#save");
const cancelButton = document.querySelector("#cancel");

let editingId = null;

const money = new Intl.NumberFormat("es-AR", { style: "currency", currency: "ARS" });

function setMessage(text, kind) {
  message.textContent = text;
  message.className = "message" + (kind ? " " + kind : "");
}

function payloadFromForm() {
  return {
    nombre: form.nombre.value.trim(),
    cargo: form.cargo.value.trim(),
    departamento: form.departamento.value.trim(),
    salario: Number(form.salario.value),
    correo: form.correo.value.trim(),
    fechaIngreso: form.fechaIngreso.value
  };
}

function fillForm(employee) {
  form.nombre.value = employee.nombre;
  form.cargo.value = employee.cargo;
  form.departamento.value = employee.departamento;
  form.salario.value = employee.salario;
  form.correo.value = employee.correo;
  form.fechaIngreso.value = employee.fechaIngreso;
}

function enterCreateMode() {
  editingId = null;
  form.reset();
  formTitle.textContent = "Nuevo empleado";
  formHint.textContent = "POST /employees";
  saveButton.textContent = "Crear empleado";
  cancelButton.hidden = true;
}

function enterEditMode(employee) {
  editingId = employee.id;
  fillForm(employee);
  formTitle.textContent = "Editar empleado #" + employee.id;
  formHint.textContent = "PUT /employees/" + employee.id;
  saveButton.textContent = "Guardar cambios";
  cancelButton.hidden = false;
  form.nombre.focus();
}

async function readError(response) {
  const text = await response.text();
  return text || ("Error " + response.status);
}

async function loadEmployees() {
  const response = await fetch("/employees");
  if (!response.ok) {
    throw new Error(await readError(response));
  }
  const employees = await response.json();
  rows.innerHTML = "";
  empty.hidden = employees.length > 0;

  for (const employee of employees) {
    const tr = document.createElement("tr");
    tr.innerHTML = `
      <td>${employee.id}</td>
      <td>${escapeHtml(employee.nombre)}</td>
      <td>${escapeHtml(employee.cargo)}</td>
      <td>${escapeHtml(employee.departamento)}</td>
      <td>${money.format(employee.salario)}</td>
      <td>${escapeHtml(employee.correo)}</td>
      <td>${escapeHtml(employee.fechaIngreso)}</td>
      <td class="row-actions"></td>`;

    const actions = tr.querySelector(".row-actions");
    const edit = document.createElement("button");
    edit.type = "button";
    edit.className = "ghost";
    edit.textContent = "Editar";
    edit.addEventListener("click", () => enterEditMode(employee));

    const remove = document.createElement("button");
    remove.type = "button";
    remove.className = "danger";
    remove.textContent = "Eliminar";
    remove.addEventListener("click", () => removeEmployee(employee));

    actions.append(edit, remove);
    rows.append(tr);
  }
}

async function removeEmployee(employee) {
  if (!confirm("¿Eliminar a " + employee.nombre + "?")) {
    return;
  }
  const response = await fetch("/employees/" + employee.id, { method: "DELETE" });
  if (!response.ok && response.status !== 204) {
    setMessage(await readError(response), "error");
    return;
  }
  if (editingId === employee.id) {
    enterCreateMode();
  }
  setMessage("Empleado eliminado con DELETE /employees/" + employee.id, "ok");
  await loadEmployees();
}

function escapeHtml(value) {
  return String(value ?? "")
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;");
}

form.addEventListener("submit", async (event) => {
  event.preventDefault();
  const body = JSON.stringify(payloadFromForm());
  const editing = editingId !== null;
  const response = await fetch(editing ? "/employees/" + editingId : "/employees", {
    method: editing ? "PUT" : "POST",
    headers: { "Content-Type": "application/json" },
    body
  });

  if (!response.ok) {
    setMessage(await readError(response), "error");
    return;
  }

  const saved = await response.json();
  setMessage(
    editing
      ? "Empleado actualizado con PUT /employees/" + saved.id
      : "Empleado creado con POST /employees (" + saved.id + ")",
    "ok"
  );
  enterCreateMode();
  await loadEmployees();
});

cancelButton.addEventListener("click", () => {
  enterCreateMode();
  setMessage("");
});

document.querySelector("#refresh").addEventListener("click", async () => {
  try {
    await loadEmployees();
    setMessage("Lista actualizada con GET /employees", "ok");
  } catch (error) {
    setMessage(error.message, "error");
  }
});

loadEmployees().catch((error) => setMessage(error.message, "error"));
