package com.example.drugsafety.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.drugsafety.entity.acl.Setting;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface SettingRepository extends JpaRepository<Setting, String> {

    @Query
    public List<Setting> findActiveByName(String name);

    @Query
    public List<Setting> findActive();
}
