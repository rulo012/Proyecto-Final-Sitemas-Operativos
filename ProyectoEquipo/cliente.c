#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>

#define TAM_BUFFER 512
#define PUERTO 4600

int main(int argc, char *argv[]) {
    int conexion_cliente;
    struct sockaddr_in direccion_servidor;
    char datos_envio[TAM_BUFFER];

    if (argc != 2) {
        fprintf(stderr, "Modo de uso: %s <IP del servidor>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    conexion_cliente = socket(AF_INET, SOCK_STREAM, 0);
    if (conexion_cliente < 0) {
        perror("Fallo al abrir conexión");
        exit(EXIT_FAILURE);
    }

    memset(&direccion_servidor, 0, sizeof(direccion_servidor));
    direccion_servidor.sin_family = AF_INET;
    direccion_servidor.sin_addr.s_addr = inet_addr(argv[1]);
    direccion_servidor.sin_port = htons(PUERTO);

    if (connect(conexion_cliente, (struct sockaddr *)&direccion_servidor, sizeof(direccion_servidor)) < 0) {
        perror("Fallo al conectar con servidor");
        exit(EXIT_FAILURE);
    }

    printf("Escribe tu mensaje: ");
    fgets(datos_envio, TAM_BUFFER, stdin);
    write(conexion_cliente, datos_envio, strlen(datos_envio));

    read(conexion_cliente, datos_envio, TAM_BUFFER);
    printf("Mensaje del servidor: %s\n", datos_envio);

    close(conexion_cliente);
    return 0;
}