package com.marketplace.repositories;

import com.marketplace.entities.Organisation;
import com.marketplace.entities.OrderItem;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrganisationRepository extends CassandraRepository<Organisation, UUID> {

    // Récupérer toutes les organisations
    @Query("SELECT * FROM organisations")
    List<Organisation> findAllOrganisations();

    // Récupérer les produits d'une organisation spécifique
    @Query("SELECT * FROM order_items WHERE organization_id = ?0 ALLOW FILTERING")
    List<OrderItem> findProductsByOrganisationId(UUID organisationId);
}
