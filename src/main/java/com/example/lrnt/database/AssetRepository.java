package com.example.lrnt.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface AssetRepository extends JpaRepository<DatabaseAsset, Long> {
    List<DatabaseAsset>  getDatabaseAssetById(String id);
    List<DatabaseAsset> getDatabaseAssetByUserId(String id);

    @Modifying
    @Transactional
    @Query(value = "SELECT * FROM lrnt.assets", nativeQuery = true)
    List<DatabaseAsset> getAll();
}
