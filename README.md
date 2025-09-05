
## Escuela Colombiana de Ingeniería
### Arquitecturas de Software – ARSW


#### Ejercicio – programación concurrente, condiciones de carrera y sincronización de hilos. EJERCICIO INDIVIDUAL O EN PAREJAS.

##### Parte I – Antes de terminar la clase.

Control de hilos con wait/notify. Productor/consumidor.

1. Revise el funcionamiento del programa y ejecútelo. Mientras esto ocurren, ejecute jVisualVM y revise el consumo de CPU del proceso correspondiente. A qué se debe este consumo?, cual es la clase responsable?
   <img width="733" height="495" alt="image" src="https://github.com/user-attachments/assets/a7f8d330-8b9a-49d8-8513-e50e76da2377" />
 ``` txt
   El alto uso de CPU ocurre porque el hilo del consumidor está ejecutándose en un bucle infinito.
   La clase que provoca este comportamiento es Consumer.
```
2. Haga los ajustes necesarios para que la solución use más eficientemente la CPU, teniendo en cuenta que -por ahora- la producción es lenta y el consumo es rápido. Verifique con JVisualVM que el consumo de CPU se reduzca.

<img width="462" height="403" alt="image" src="https://github.com/user-attachments/assets/98e516cf-0029-4d57-a5e8-bb3d7812e3c1" />

<img width="921" height="531" alt="image" src="https://github.com/user-attachments/assets/ccc9c3e2-3e0b-45fc-8067-5e29af30ac05" />

3. Haga que ahora el productor produzca muy rápido, y el consumidor consuma lento. Teniendo en cuenta que el productor conoce un límite de Stock (cuantos elementos debería tener, a lo sumo en la cola), haga que dicho límite se respete. Revise el API de la colección usada como cola para ver cómo garantizar que dicho límite no se supere. Verifique que, al poner un límite pequeño para el 'stock', no haya consumo alto de CPU ni errores.
<img width="967" height="178" alt="image" src="https://github.com/user-attachments/assets/7d1b7bd8-92bf-4778-96f0-80e02261c1c9" />
<img width="721" height="67" alt="image" src="https://github.com/user-attachments/assets/475e11f3-b011-48b7-8160-b7aa53cbc9ff" />
<img width="1217" height="501" alt="image" src="https://github.com/user-attachments/assets/56ea95a6-e26d-415b-8c15-6756fd963526" />


##### Parte II. – Antes de terminar la clase.

Teniendo en cuenta los conceptos vistos de condición de carrera y sincronización, haga una nueva versión -más eficiente- del ejercicio anterior (el buscador de listas negras). En la versión actual, cada hilo se encarga de revisar el host en la totalidad del subconjunto de servidores que le corresponde, de manera que en conjunto se están explorando la totalidad de servidores. Teniendo esto en cuenta, haga que:

- La búsqueda distribuida se detenga (deje de buscar en las listas negras restantes) y retorne la respuesta apenas, en su conjunto, los hilos hayan detectado el número de ocurrencias requerido que determina si un host es confiable o no (_BLACK_LIST_ALARM_COUNT_).
- Lo anterior, garantizando que no se den condiciones de carrera.

##### Parte III. – Avance para el martes, antes de clase.

Sincronización y Dead-Locks.

![](http://files.explosm.net/comics/Matt/Bummed-forever.png)

1. Revise el programa “highlander-simulator”, dispuesto en el paquete edu.eci.arsw.highlandersim. Este es un juego en el que:

	* Se tienen N jugadores inmortales.
	* Cada jugador conoce a los N-1 jugador restantes.
	* Cada jugador, permanentemente, ataca a algún otro inmortal. El que primero ataca le resta M puntos de vida a su contrincante, y aumenta en esta misma cantidad sus propios puntos de vida.
	* El juego podría nunca tener un único ganador. Lo más probable es que al final sólo queden dos, peleando indefinidamente quitando y sumando puntos de vida.

2. Revise el código e identifique cómo se implemento la funcionalidad antes indicada. Dada la intención del juego, un invariante debería ser que la sumatoria de los puntos de vida de todos los jugadores siempre sea el mismo(claro está, en un instante de tiempo en el que no esté en proceso una operación de incremento/reducción de tiempo). Para este caso, para N jugadores, cual debería ser este valor?.
``` txt
El valor tendria que ser N *  DEFAULT_IMMORTAL_HEALTH
```
3. Ejecute la aplicación y verifique cómo funcionan las opción ‘pause and check’. Se cumple el invariante?.
``` txt
Aquí tienes una versión parafraseada:

El invariante no se mantiene, pues la suma de los puntos de vida de todos los jugadores no coincide. El resultado esperado debería ser:
N: 3
DEFAULT_IMMORTAL_HEALTH: 100
300 pero es 540 como se ve.

```
<img width="581" height="263" alt="image" src="https://github.com/user-attachments/assets/2315ee7f-d116-44da-bfd4-eec1c5315b97" />


4. Una primera hipótesis para que se presente la condición de carrera para dicha función (pause and check), es que el programa consulta la lista cuyos valores va a imprimir, a la vez que otros hilos modifican sus valores. Para corregir esto, haga lo que sea necesario para que efectivamente, antes de imprimir los resultados actuales, se pausen todos los demás hilos. Adicionalmente, implemente la opción ‘resume’.
``` txt
Se implementa la acción de los botones btnPauseAndCheck y btnResume
```
<img width="937" height="437" alt="image" src="https://github.com/user-attachments/assets/ac49f8a3-a96d-409f-9a77-dd5cb5c7781e" />
<img width="1363" height="564" alt="image" src="https://github.com/user-attachments/assets/06da8eef-f7fb-4734-90c3-977a59b8f774" />




5. Verifique nuevamente el funcionamiento (haga clic muchas veces en el botón). Se cumple o no el invariante?.
``` txt
NO cumple
```
<img width="542" height="235" alt="image" src="https://github.com/user-attachments/assets/90f1dd9a-5e9a-4a19-829d-f5c73685c2f8" />

6. Identifique posibles regiones críticas en lo que respecta a la pelea de los inmortales. Implemente una estrategia de bloqueo que evite las condiciones de carrera. Recuerde que si usted requiere usar dos o más ‘locks’ simultáneamente, puede usar bloques sincronizados anidados:

	```java
	synchronized(locka){
		synchronized(lockb){
			…
		}
	}
	```
<img width="1622" height="698" alt="image" src="https://github.com/user-attachments/assets/6557a25a-d1d6-4fb5-b391-7e6c847b4949" />

``` txt
Se implementa la estrategia de bloqueo en el método fight() de la clase Immortal:

```

7. Tras implementar su estrategia, ponga a correr su programa, y ponga atención a si éste se llega a detener. Si es así, use los programas jps y jstack para identificar por qué el programa se detuvo.
``` txt
Al ejecutar el comando, pudimos ver los procesos que se estaban ejecutando y sus respectivos ID:
jps -l
```
<img width="857" height="233" alt="image" src="https://github.com/user-attachments/assets/e972985d-2676-481a-945a-e888fc845f09" />

``` txt
Luego con el ID del proceso que se estaba ejecutando, se ejecutó el comando jstack y se encontró un deadlock:

jstack  28536
```

<img width="1761" height="347" alt="image" src="https://github.com/user-attachments/assets/cb508812-9047-47d2-8add-98b54f4b31ed" />

8. Plantee una estrategia para corregir el problema antes identificado (puede revisar de nuevo las páginas 206 y 207 de _Java Concurrency in Practice_).

``` txt
Se implementa la estrategia de ordenar los locks de manera que se evite el deadlock en el método minImmortalHash() de la clase Immortal:
```
<img width="1898" height="597" alt="image" src="https://github.com/user-attachments/assets/842a450f-be29-4d3e-bcbf-2a5607fd190c" />

9. Una vez corregido el problema, rectifique que el programa siga funcionando de manera consistente cuando se ejecutan 100, 1000 o 10000 inmortales. Si en estos casos grandes se empieza a incumplir de nuevo el invariante, debe analizar lo realizado en el paso 4.
- 100 inmortales 
<img width="935" height="394" alt="image" src="https://github.com/user-attachments/assets/71483255-7751-40cc-ba90-46ace64c3dd8" />
- 1000 inmortales
<img width="1035" height="505" alt="image" src="https://github.com/user-attachments/assets/1cd8abdb-89a7-4c1c-b048-0489607b14db" />
- 10000 inmortales
<img width="950" height="394" alt="image" src="https://github.com/user-attachments/assets/ced2f691-b98f-49b5-bafd-8915010f103c" />

10. Un elemento molesto para la simulación es que en cierto punto de la misma hay pocos 'inmortales' vivos realizando peleas fallidas con 'inmortales' ya muertos. Es necesario ir suprimiendo los inmortales muertos de la simulación a medida que van muriendo. Para esto:
	* Analizando el esquema de funcionamiento de la simulación, esto podría crear una condición de carrera? Implemente la funcionalidad, ejecute la simulación y observe qué problema se presenta cuando hay muchos 'inmortales' en la misma. Escriba sus conclusiones al respecto en el archivo RESPUESTAS.txt.
	* Corrija el problema anterior __SIN hacer uso de sincronización__, pues volver secuencial el acceso a la lista compartida de inmortales haría extremadamente lenta la simulación.

11. Para finalizar, implemente la opción STOP.

``` txt
Se implementa la accion del boton btnStop
```
<img width="1054" height="389" alt="image" src="https://github.com/user-attachments/assets/2404c53b-c685-45b7-8456-8f024db36aee" />

<!--
### Criterios de evaluación

1. Parte I.
	* Funcional: La simulación de producción/consumidor se ejecuta eficientemente (sin esperas activas).

2. Parte II. (Retomando el laboratorio 1)
	* Se modificó el ejercicio anterior para que los hilos llevaran conjuntamente (compartido) el número de ocurrencias encontradas, y se finalizaran y retornaran el valor en cuanto dicho número de ocurrencias fuera el esperado.
	* Se garantiza que no se den condiciones de carrera modificando el acceso concurrente al valor compartido (número de ocurrencias).


2. Parte III.
	* Diseño:
		- Coordinación de hilos:
			* Para pausar la pelea, se debe lograr que el hilo principal induzca a los otros a que se suspendan a sí mismos. Se debe también tener en cuenta que sólo se debe mostrar la sumatoria de los puntos de vida cuando se asegure que todos los hilos han sido suspendidos.
			* Si para lo anterior se recorre a todo el conjunto de hilos para ver su estado, se evalúa como R, por ser muy ineficiente.
			* Si para lo anterior los hilos manipulan un contador concurrentemente, pero lo hacen sin tener en cuenta que el incremento de un contador no es una operación atómica -es decir, que puede causar una condición de carrera- , se evalúa como R. En este caso se debería sincronizar el acceso, o usar tipos atómicos como AtomicInteger).

		- Consistencia ante la concurrencia
			* Para garantizar la consistencia en la pelea entre dos inmortales, se debe sincronizar el acceso a cualquier otra pelea que involucre a uno, al otro, o a los dos simultáneamente:
			* En los bloques anidados de sincronización requeridos para lo anterior, se debe garantizar que si los mismos locks son usados en dos peleas simultánemante, éstos será usados en el mismo orden para evitar deadlocks.
			* En caso de sincronizar el acceso a la pelea con un LOCK común, se evaluará como M, pues esto hace secuencial todas las peleas.
			* La lista de inmortales debe reducirse en la medida que éstos mueran, pero esta operación debe realizarse SIN sincronización, sino haciendo uso de una colección concurrente (no bloqueante).

	

	* Funcionalidad:
		* Se cumple con el invariante al usar la aplicación con 10, 100 o 1000 hilos.
		* La aplicación puede reanudar y finalizar(stop) su ejecución.
		
		-->

<a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/"><img alt="Creative Commons License" style="border-width:0" src="https://i.creativecommons.org/l/by-nc/4.0/88x31.png" /></a><br />Este contenido hace parte del curso Arquitecturas de Software del programa de Ingeniería de Sistemas de la Escuela Colombiana de Ingeniería, y está licenciado como <a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/">Creative Commons Attribution-NonCommercial 4.0 International License</a>.
