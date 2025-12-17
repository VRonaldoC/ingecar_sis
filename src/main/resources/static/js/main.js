document.addEventListener('DOMContentLoaded', function() {
    console.log('Sistema INGECAR iniciado');
    
    // Confirmar eliminaciones
    document.querySelectorAll('.btn-danger').forEach(btn => {
        btn.addEventListener('click', function(e) {
            if (!confirm('¿Está seguro?')) e.preventDefault();
        });
    });
});