package api.config;

import api.domain.models.Creator;
import api.domain.models.NivelSuscripcion;
import api.domain.models.UserEntity;
import api.application.services.CreatorService;
import api.application.services.NivelSuscripcionService;
import api.application.services.UserService;
import api.infrastructure.api.utils.TiposSuscripcion;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashSet;

@Component
public class Initializer implements CommandLineRunner {

    final UserService userService;
    final CreatorService creatorService;
    final NivelSuscripcionService nivelSuscripcionService;

    Initializer(UserService userService, CreatorService creatorService, NivelSuscripcionService nivelSuscripcionService) {
        this.userService = userService;
        this.creatorService = creatorService;
        this.nivelSuscripcionService = nivelSuscripcionService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userService.getAll().toArray().length == 0) {

            // Creando los usuarios
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail("EMAIL@email.com");
            userEntity.setUsername("Nombre");
            userEntity.setPassword("USUARIO");

            userService.save(userEntity);

            UserEntity usuarioCreador = new UserEntity();
            usuarioCreador.setPassword("creador");
            usuarioCreador.setUsername("Creador");
            usuarioCreador.setEmail("creador@example.com");

            userService.save(usuarioCreador);

            // Crear entidad Creator
            Creator creator = new Creator();
            creator.setUsuario(usuarioCreador);
            creator.setDescripcion("Este es un creador de contenido");
            creator.setNiveles(new HashSet<>());
            creator = creatorService.save(creator);

            // Crear niveles de suscripción para el creador
            NivelSuscripcion nivel1 = new NivelSuscripcion();
            nivel1.setTiposSuscripcion(TiposSuscripcion.BASIC);
            nivel1.setDescripcion("Suscripción básica");
            nivel1.setPrecioMensual(BigDecimal.valueOf(9.99));
            nivel1.setCreador(creator);

            NivelSuscripcion nivel2 = new NivelSuscripcion();
            nivel2.setTiposSuscripcion(TiposSuscripcion.PREMIUM);
            nivel2.setDescripcion("Suscripción premium");
            nivel2.setPrecioMensual(BigDecimal.valueOf(19.99));
            nivel2.setCreador(creator);

            nivelSuscripcionService.save(nivel1);
            nivelSuscripcionService.save(nivel2);

            // Agregar los niveles al creador
            creator.getNiveles().add(nivel1);
            creator.getNiveles().add(nivel2);
            creatorService.save(creator);

            System.out.println("Datos iniciales creados.");
        } else {
            System.out.println("Los datos ya existen. No se realiza ninguna acción.");
        }
    }

}
