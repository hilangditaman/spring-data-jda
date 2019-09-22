package com.dana.bijak.repository;

import com.dana.bijak.entity.Pinjaman;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PinjamanRepository extends CrudRepository<Pinjaman, Long> {
    List<Pinjaman> findByPhoneNumber(String phoneNumber);
}
