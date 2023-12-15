#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <pthread.h>

#define TAM_BUFFER 512
#define PUERTO_SERVIDOR 5600

void convertir_mayusculas(char *texto) {
    while (*texto) {
        *texto = toupper((unsigned char) *texto);
        texto++;
    }
}

void *atender_cliente(void *conexion_ptr) {
    int conexion = *(int *)conexion_ptr;
    free(conexion_ptr);

    char datos[TAM_BUFFER];

    read(conexion, datos, TAM_BUFFER);

    convertir_mayusculas(datos);

    write(conexion, datos, strlen(datos));

    close(conexion);
    pthread_exit(NULL);
}

int main() {
    int socket_serv, *conexion_cliente_ptr;
    struct sockaddr_in direccion_serv, direccion_cliente;
    socklen_t tam_dir_cliente = sizeof(direccion_cliente);

    socket_serv = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_serv < 0) {
        perror("Error en apertura de socket servidor");
        exit(EXIT_FAILURE);
    }

    memset(&direccion_serv, 0, sizeof(direccion_serv));
    direccion_serv.sin_family = AF_INET;
    direccion_serv.sin_addr.s_addr = htonl(INADDR_ANY);
    direccion_serv.sin_port = htons(PUERTO_SERVIDOR);

    if (bind(socket_serv, (struct sockaddr *)&direccion_serv, sizeof(direccion_serv)) < 0) {
        perror("Error en enlace de socket servidor");
        close(socket_serv);
        exit(EXIT_FAILURE);
    }

    listen(socket_serv, 5);

    while (1) {
        conexion_cliente_ptr = malloc(sizeof(int));
        *conexion_cliente_ptr = accept(socket_serv, (struct sockaddr *)&direccion_cliente, &tam_dir_cliente);
        if (*conexion_cliente_ptr < 0) {
            perror("Fallo en conexión con router");
            free(conexion_cliente_ptr);
            continue;
        }

        pthread_t hilo_serv;
        if (pthread_create(&hilo_serv, NULL, atender_cliente, conexion_cliente_ptr) != 0) {
            perror("Error al crear hilo para router");
            close(*conexion_cliente_ptr);
            free(conexion_cliente_ptr);
        }
    }

    close(socket_serv);
    return 0;
}