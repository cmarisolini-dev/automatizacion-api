# Registro de incidencias del servicio externo

Fecha local: 23/11/2026. Ambiente: Swagger PetStore v2 público.

| Incidencia | Operación | Entrada | Esperado | Observado V1 | Estado |
|---|---|---|---|---|---|
| DEF_CP02 | POST /pet | JSON con id no numérico | Rechazo 400/405 | 500 | Detectado, no corregido en servidor |
| DEF_CP06 | PUT /pet | JSON con id no numérico | Rechazo 400/405 | 500 | Detectado, no corregido en servidor |

La ejecución Maven V1 registró 20 ejecuciones, 18 aprobadas, 2 fallidas, 0 errores de ejecución y 0 omitidas. Se conservan los escenarios estrictos en la suite KnownDefectsIT.

## Validación manual del caso alternativo (23/11/2026, 22:47 hora Perú)

```powershell
curl.exe -i -X POST "https://petstore.swagger.io/v2/pet" -H "Content-Type: text/plain" --data-binary "invalid"
curl.exe -i -X PUT "https://petstore.swagger.io/v2/pet" -H "Content-Type: text/plain" --data-binary "invalid"
```

Ambas solicitudes respondieron HTTP 415 Unsupported Media Type, con Content-Type application/xml. Se automatizan estos casos como CP02 y CP06 en la suite de aceptación. No se acepta HTTP 500 como resultado correcto. La V2 requiere una nueva ejecución Maven para confirmar sus resultados.
