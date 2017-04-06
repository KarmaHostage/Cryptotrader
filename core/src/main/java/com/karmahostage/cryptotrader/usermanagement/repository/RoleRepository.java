package com.karmahostage.cryptotrader.usermanagement.repository;

import com.karmahostage.cryptotrader.usermanagement.domain.Role;
import com.karmahostage.cryptotrader.infrastructure.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
