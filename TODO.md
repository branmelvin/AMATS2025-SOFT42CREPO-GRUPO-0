# Plan para Implementar Notificaciones Toast de Bajo Stock

## Información Recopilada
- El dashboard se maneja en `HomeController.java` con el endpoint `/dashboard`.
- `InventarioService` tiene el método `obtenerBajoStock()` que devuelve items con cantidad actual < 5.
- Las notificaciones deben mostrarse solo al administrador al iniciar sesión (dashboard).
- Se usará Toastify.js para las notificaciones toast.

## Plan de Implementación
1. **Agregar Toastify.js al layout.html**: Incluir el CDN de Toastify.js en la sección de scripts para que esté disponible globalmente.
2. **Modificar HomeController.java**: 
   - Inyectar `Authentication` para verificar si el usuario es administrador.
   - Si es admin, obtener la lista de bajo stock y pasarla al modelo como `lowStockItems`.
3. **Modificar dashboard.html**: 
   - Agregar un script que verifique si `lowStockItems` no está vacío y muestre un toast para cada item con bajo stock.
   - Los toast deben ser de tipo warning o error para alertar sobre bajo stock.

## Archivos a Editar
- `src/main/resources/templates/layout.html`: Agregar CDN de Toastify.js.
- `src/main/java/com/itca/inventario/controller/HomeController.java`: Modificar el método dashboard para incluir bajo stock si es admin.
- `src/main/resources/templates/dashboard.html`: Agregar script para mostrar toasts.

## Pasos de Seguimiento
- [] Agregar Toastify.js al layout.html.
- [] Modificar HomeController para pasar bajo stock al modelo solo para admins.
- [] Agregar script en dashboard.html para mostrar toasts de bajo stock.
- [] Probar la funcionalidad iniciando sesión como admin y verificando toasts.
