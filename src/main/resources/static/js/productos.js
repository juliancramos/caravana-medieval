document.addEventListener("DOMContentLoaded", function () {
    cargarProductos();

    document.getElementById("productoForm").addEventListener("submit", function (event) {
        event.preventDefault();
        guardarProducto();
    });
});

function cargarProductos() {
    fetch("/producto/listar")
        .then(response => response.json())
        .then(data => {
            let tabla = document.getElementById("productosTable");
            tabla.innerHTML = "";
            data.forEach(producto => {
                let fila = `
                            <tr>
                                <td>${producto.idProducto}</td>
                                <td>${producto.nombre}</td>
                                <td>${producto.descripcion}</td>
                                <td>${producto.peso}</td>
                                <td>
                                    <button onclick="editarProducto(${producto.idProducto})">Editar</button>
                                    <button onclick="eliminarProducto(${producto.idProducto})">Eliminar</button>
                                </td>
                            </tr>
                        `;
                tabla.innerHTML += fila;
            });
        })
        .catch(error => console.error("Error al cargar productos:", error));
}

function guardarProducto() {
    let id = document.getElementById("idProducto").value;
    let nombre = document.getElementById("nombre").value;
    let descripcion = document.getElementById("descripcion").value;
    let peso = document.getElementById("peso").value;

    let producto = { nombre, descripcion, peso };

    //Si id contiene valor quiere decir que se está realizando una modificación con put
    //Si no contiene id quiere decir que no existe, así que se crea con post
    let metodo = id ? "PUT" : "POST";
    let url = id ? `/producto/actualizarCompleto/${id}` : "/producto/crearProducto";

    fetch(url, {
        method: metodo,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(producto)
    })
        .then(response => response.json())
        .then(() => {
            document.getElementById("productoForm").reset();
            cargarProductos();
        })
        .catch(error => console.error("Error al guardar:", error));
}

function editarProducto(id) {
    fetch(`/producto/${id}`)
        .then(response => response.json())
        .then(producto => {
            document.getElementById("idProducto").value = producto.idProducto;
            document.getElementById("nombre").value = producto.nombre;
            document.getElementById("descripcion").value = producto.descripcion;
            document.getElementById("peso").value = producto.peso;
        })
        .catch(error => console.error("Error al cargar producto:", error));
}

function eliminarProducto(id) {
    Swal.fire({
        title: "¿Estás seguro?",
        text: "Esta acción no se puede deshacer.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#3085d6",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed) {
            fetch(`/producto/eliminar/${id}`, { method: "DELETE" })
                .then(() => {
                    cargarProductos();
                    Swal.fire("Eliminado", "El producto ha sido eliminado.", "success");
                })
                .catch(error => console.error("Error al eliminar:", error));
        }
    });
}
