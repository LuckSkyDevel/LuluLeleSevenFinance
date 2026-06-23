package com.finance.leluseven.usuario.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUsuarioJpaRepository extends JpaRepository<UsuarioEntity, Long> {
    Optional<UsuarioEntity> findByNomUsuario(String nomUsuario);

    Optional<UsuarioEntity> findByEmailUsuario(String emailUsuario);
}
