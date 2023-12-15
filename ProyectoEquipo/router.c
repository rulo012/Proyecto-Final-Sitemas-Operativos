#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <pthread.h>

#define TAM_DATOS 512
#define PUERTO_ROUTER 4600
#define IP_SERVIDOR "127.0.0.1"
#define PUERTO_SERVIDOR 5600

void *procesar_cliente(void *conexion_ptr) {
    int conexion = *(int *)conexion_ptr;
    free(conexion_ptr);

    char datos[TAM_DATOS];
    ssize_t bytes_recibidos;

    bytes_recibidos = read(conexion, datos, TAM_DATOS);
    if (bytes_recibidos < 0) {
        perror("Error en recepción de datos cliente");
        close(conexion);
        pthread_exit(NULL);
    }

    int conexion_serv = socket(AF_INET, SOCK_STREAM, 0);
    if (conexion_serv < 0) {
        perror("Fallo creación socket servidor");
        close(conexion);
        pthread_exit(NULL);
    }

    struct sockaddr_in dir_serv;
    memset(&dir_serv, 0, sizeof(dir_serv));
    dir_serv.sin_family = AF_INET;
    dir_serv.sin_addr.s_addr = inet_addr(IP_SERVIDOR);
    dir_serv.sin_port = htons(PUERTO_SERVIDOR);

    if (connect(conexion_serv, (struct sockaddr *)&dir_serv, sizeof(dir_serv)) < 0) {
        perror("Fallo al conectar con servidor");
        close(conexion);
        close(conexion_serv);
        pthread_exit(NULL);
    }

    write(conexion_serv, datos, bytes_recibidos);

    bytes_recibidos = read(conexion_serv, datos, TAM_DATOS);
    if (bytes_recibidos < 0) {
        perror("Error en lectura de servidor");
        close(conexion);
        close(conexion_serv);
        pthread_exit(NULL);
    }

    write(conexion, datos, bytes_recibidos);

    close(conexion);
    close(conexion_serv);
    pthread_exit(NULL);
}

int main() {
    int socket_enrutador, *conexion_cliente_ptr;
    struct sockaddr_in direccion_enrutador, direccion_cliente;
    socklen_t tam_dir_cliente = sizeof(direccion_cliente);

    socket_enrutador = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_enrutador < 0) {
        perror("Error al abrir socket enrutador");
        exit(EXIT_FAILURE);
    }

    memset(&direccion_enrutador, 0, sizeof(direccion_enrutador));
    direccion_enrutador.sin_family = AF_INET;
    direccion_enrutador.sin_addr.s_addr = htonl(INADDR_ANY);
    direccion_enrutador.sin_port = htons(PUERTO_ROUTER);

    if (bind(socket_enrutador, (struct sockaddr *)&direccion_enrutador, sizeof(direccion_enrutador)) < 0) {
        perror("Error al enlazar enrutador");
        close(socket_enrutador);
        exit(EXIT_FAILURE);
    }

    listen(socket_enrutador, 5);

    while (1) {
        conexion_cliente_ptr = malloc(sizeof(int));
        *conexion_cliente_ptr = accept(socket_enrutador, (struct sockaddr *)&direccion_cliente, &tam_dir_cliente);
        if (*conexion_cliente_ptr < 0) {
            perror("Fallo en conexión cliente");
            free(conexion_cliente_ptr);
            continue;
        }

        pthread_t hilo_cliente;
        if (pthread_create(&hilo_cliente, NULL, procesar_cliente, conexion_cliente_ptr) != 0) {
            perror("Error al iniciar hilo de cliente");
            close(*conexion_cliente_ptr);
            free(conexion_cliente_ptr);
        }
    }

    close(socket_enrutador);
    return 0;
}