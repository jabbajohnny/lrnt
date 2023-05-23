package com.example.lrnt.database;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<DatabaseAsset, Long> {
    List<DatabaseAsset>  getDatabaseAssetById(String id);
}
