document.getElementById("loginForm").addEventListener("submit", async function(event) {
    event.preventDefault(); // Evita el refresco de la página

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    try {
        const response = await fetch("/api/login", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ username, password })
        });

        const data = await response.json();

        if (data.success) {
            window.location.href = "inicio.html"; // Redirige si el login es exitoso
        } else {
            alert("Error: " + data.message); // Muestra un mensaje si hay error
        }
    } catch (error) {
        console.error("Error en la solicitud:", error);
        alert("Hubo un problema con el servidor.");
    }
});

document.addEventListener("DOMContentLoaded", function() {
    document.getElementById("productoForm").addEventListener("submit", function(e) {
      e.preventDefault();
      const formData = new FormData(this);
      
      const data = {
        nombre: formData.get("nombre"),
        descripcion: formData.get("descripcion"),
        peso: parseFloat(formData.get("peso"))
      };
  
      fetch("/producto/crearProducto", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
      })
      .then(response => response.json())
      .then(result => {
        console.log("Producto creado:", result);
        // Aquí puedes actualizar la interfaz o notificar al usuario
      })
      .catch(error => console.error("Error:", error));
    });
  });
  