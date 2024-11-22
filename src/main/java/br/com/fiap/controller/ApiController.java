package br.com.fiap.controller;

import br.com.fiap.dto.DispositivosDto;
import br.com.fiap.dto.UsuariosDto;
import br.com.fiap.models.classes.Dispositivos;
import br.com.fiap.models.classes.Usuarios;
import br.com.fiap.models.entities.Categoria;
import br.com.fiap.services.DispositivosService;
import br.com.fiap.services.DispositivosServiceFactory;
import br.com.fiap.services.UsuariosService;
import br.com.fiap.services.UsuariosServiceFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Path("/rest")
public class ApiController {
    private final UsuariosService usuariosService = UsuariosServiceFactory.create();
    private final DispositivosService dispositivosService = DispositivosServiceFactory.create();

    // CRUD de Usuarios

    @POST
    @Path("/usuarios")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUsuarios(UsuariosDto usuariosDto) {
        try {
            Usuarios usuarios = new Usuarios(usuariosDto.getNome(), usuariosDto.getEmail(), usuariosDto.getSenha(), usuariosDto.getCEP());
            usuariosService.cadastrarUsuarios(usuarios);
            return Response.status(Response.Status.CREATED).entity(usuariosDto).build();
        } catch (SQLException | IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }



    @GET
    @Path("/usuarios/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios(@PathParam("email") String email) {
        try {
            Usuarios usuarios = usuariosService.buscarUsuariosPorEmail(email);
            if (usuarios == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            UsuariosDto usuariosDto = new UsuariosDto();
            usuariosDto.setNome(usuarios.getNome());
            usuariosDto.setEmail(usuarios.getEmail());
            usuariosDto.setSenha(usuarios.getSenha());
            usuariosDto.setCEP(usuarios.getCEP());
            return Response.ok(usuariosDto).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarLogin(UsuariosDto usuariosDto) {
        try {
            // Validação dos dados recebidos
            if (usuariosDto == null || usuariosDto.getEmail() == null || usuariosDto.getSenha() == null) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Email e senha são obrigatórios")
                        .build();
            }

            // Busca do usuário pelo email
            Usuarios usuarioEncontrado = usuariosService.buscarUsuariosPorEmail(usuariosDto.getEmail());

            // Valida credenciais
            if (usuarioEncontrado != null && usuarioEncontrado.getSenha().equals(usuariosDto.getSenha())) {
                return Response.ok(usuariosDto).build();
            } else {
                return Response.status(Response.Status.UNAUTHORIZED)
                        .entity("Email ou senha incorretos")
                        .build();
            }
        } catch (SQLException e) {
            // Tratamento de exceções do banco de dados
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro ao processar a solicitação: " + e.getMessage())
                    .build();
        } catch (Exception e) {
            // Tratamento de outras exceções
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Erro inesperado: " + e.getMessage())
                    .build();
        }
    }



    @PUT
    @Path("/usuarios/{email}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUsuarios(@PathParam("email") String email, UsuariosDto usuariosDto) {
        try {
            Usuarios usuarios = new Usuarios(usuariosDto.getNome(), email, usuariosDto.getSenha(), usuariosDto.getCEP());
            usuariosService.atualizarUsuarios(usuarios);
            return Response.ok(usuariosDto).build();
        } catch (SQLException | IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/usuarios/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUsuarios(@PathParam("email") String email) {
        try {
            usuariosService.removerUsuarios(email);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    // CRUD de Dispositivos

    @POST
    @Path("/dispositivos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createDispositivos(DispositivosDto dispositivosDto) {
        try {
            Usuarios usuarios = usuariosService.buscarUsuariosPorEmail(dispositivosDto.getEmailUsuario());
            if (usuarios == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Usuário não encontrado").build();
            }
            Dispositivos dispositivos = new Dispositivos(Categoria.valueOf(dispositivosDto.getCategoria()), usuarios, dispositivosDto.getNome(), dispositivosDto.getConsumoDeEnergia(), dispositivosDto.getId());
            dispositivosService.cadastrarDispositivo(dispositivos);
            return Response.status(Response.Status.CREATED).entity(dispositivosDto).build();
        } catch (SQLException | IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/dispositivos/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDispositivos(@PathParam("id") Long id) {
        try {
            Dispositivos dispositivos = dispositivosService.buscarDispositivoPorId(id);
            if (dispositivos == null) {
                return Response.status(Response.Status.NOT_FOUND).build();
            }
            DispositivosDto dispositivosDto = new DispositivosDto();
            dispositivosDto.setNome(dispositivos.getNome());
            dispositivosDto.setCategoria(dispositivos.getCategoria().name());
            dispositivosDto.setId(dispositivos.getId());
            dispositivosDto.setConsumoDeEnergia(dispositivos.getConsumoDeEnergia());
            dispositivosDto.setEmailUsuario(dispositivos.getEmailUsuario());
            return Response.ok(dispositivosDto).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @PUT
    @Path("/dispositivos/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDispositivos(@PathParam("id") Long id, DispositivosDto dispositivosDto) {
        try {
            Usuarios usuarios = usuariosService.buscarUsuariosPorEmail(dispositivosDto.getEmailUsuario());
            if (usuarios == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Usuario não encontrado").build();
            }
            Dispositivos dispositivos = new Dispositivos(Categoria.valueOf(dispositivosDto.getCategoria()), usuarios, dispositivosDto.getNome(), dispositivosDto.getConsumoDeEnergia(), dispositivosDto.getId());
            dispositivosService.atualizarDispositivo(dispositivos);
            return Response.ok(dispositivosDto).build();
        } catch (SQLException | IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/dispositivos/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDispositivo(@PathParam("id") Long id) {
        try {
            dispositivosService.removerDispositivo(id);
            return Response.noContent().build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/dispositivos/email/{email}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listDispositivos(@PathParam("email") String email) {
        if (email == null || email.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email do usuário é obrigatório").build();
        }
        try {
            List<Dispositivos> dispositivos = dispositivosService.listarDispositivos(email);
            if (dispositivos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Nenhum dispositivo encontrado para o usuário").build();
            }
            List<DispositivosDto> dispositivosDtos = dispositivos.stream().map(dispositivo -> {
                DispositivosDto dto = new DispositivosDto();
                dto.setNome(dispositivo.getNome());
                dto.setCategoria(dispositivo.getCategoria().name());
                dto.setId(dispositivo.getId());
                dto.setConsumoDeEnergia(dispositivo.getConsumoDeEnergia());
                dto.setEmailUsuario(dispositivo.getEmailUsuario());
                return dto;
            }).collect(Collectors.toList());
            return Response.ok(dispositivosDtos).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }


}
