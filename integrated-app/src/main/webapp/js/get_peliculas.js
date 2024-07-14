// Espera a que el DOM se cargue completamente
document.addEventListener('DOMContentLoaded', async () => {
    // Realizamos una peticion fetch a esta api para obtener todas las peliculas de la base:
    // Configuracion de options, es un get y no necesita body
    const options = {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    };

    const response = await fetch('http://localhost:8081/integrated-app/movies', options);
    const data = await response.json();
    
    // Extraemos las peliculas de la respuesta
    const movies = data;
    
    // Obtenemos el tbody de la tabla
    const tbody = document.getElementById('bodyTablePeliculas');

    // Recorremos todas las peliculas
    movies.forEach(movie => {
        // Creamos un tr
        const tr = document.createElement('tr');
        // Creamos un td con el titulo de la pelicula
        const tdTitle = document.createElement('td');
        tdTitle.textContent = movie.title;
        // Creamos un td con la duracion de la pelicula
        const tdDuration = document.createElement('td');
        tdDuration.textContent = movie.duration;
        // Creamos un td con los generos de la pelicula
        const tdGenres = document.createElement('td');
        tdGenres.textContent = movie.genre;
        // Creamos un td con la imagen de la pelicula
        const tdImage = document.createElement('td');
        const img = document.createElement('img');
        img.src = "../assets/img/" + movie.image;
        img.width = '150';
        img.alt = movie.title;
        tdImage.appendChild(img);
        // Agrego la clase a la imagen para que se vea mejor de bootstrap fluid y thumbnail
        img.classList.add('img-fluid');
        img.classList.add('img-thumbnail');
        // Creamos un td para los botones de modificar y eliminar 
        const tdButtons = document.createElement('td');
        const deleteButton = document.createElement('button');
        deleteButton.id = movie.id;
        deleteButton.textContent = "Eliminar";
        deleteButton.classList.add('btn');
        deleteButton.classList.add('btn-danger');
        deleteButton.classList.add('btn-eliminar');
        deleteButton.style.marginRight = '10px';
        const updateButton = document.createElement('button');
        updateButton.id = movie.id;
        updateButton.textContent = "Modificar";
        updateButton.classList.add('btn');
        updateButton.classList.add('btn-primary');
        updateButton.classList.add('btn-modificar');
        tdButtons.appendChild(deleteButton);
        tdButtons.appendChild(updateButton);
        // Anadimos los td al tr
        tr.appendChild(tdTitle);
        tr.appendChild(tdDuration);
        tr.appendChild(tdGenres);
        tr.appendChild(tdImage);
        tr.appendChild(tdButtons);
        // Anadimos el tr a al body
        tbody.appendChild(tr);
    });

    // Anadiminos event listeners a los botones de eliminar
    document.querySelectorAll('.btn-eliminar').forEach(button => {
        button.addEventListener('click', async function () {
            const id = this.getAttribute('id');
            const deleteOptions = {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json'
                }
            }

            const deleteResponse = await fetch(`http://localhost:8081/integrated-app/movies?id=${id}`, deleteOptions);
            
            if(deleteResponse.ok) {
                this.closest('tr').remove();
            } else {
                alert('Error al eliminar la pelicula');
            }

        })
    });

    // Anadimos event listeners a los botones de actualizar
    document.querySelectorAll('.btn-modificar').forEach(button => {
    button.addEventListener('click', async function () {
        const id = this.getAttribute('id');
        
        // Obtener los datos actualizados de la película.
        const tituloActualizado = document.querySelector(`#titulo-${id}`).value;
        const directorActualizado = document.querySelector(`#director-${id}`).value;
        const anioActualizado = document.querySelector(`#anio-${id}`).value;
        
        // Crear el objeto de película con los datos actualizados
        const updatedMovie = {
            id: id,
            title: tituloActualizado,
            director: directorActualizado,
            year: anioActualizado
        };

        const updateOptions = {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(updatedMovie)
        };

        try {
            const updateResponse = await fetch(`http://localhost:8081/integrated-app/movies?id=${id}`, updateOptions);
            
            if(updateResponse.ok) {
                const responseData = await updateResponse.json();
                alert('Película actualizada exitosamente');
                // Opcional: Actualizar la interfaz de usuario con los nuevos datos
            } else {
                alert('Error al actualizar la película');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('Error al actualizar la película');
        }
    });
});
});