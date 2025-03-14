//package com.marketplace.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
//import org.springframework.data.cassandra.config.SchemaAction;
//import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
//import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
//
//import java.util.Collections;
//import java.util.List;
//
//@Configuration
//public class ScyllaDBConfig extends AbstractCassandraConfiguration {
//
//    @Override
//    protected String getKeyspaceName() {
//        return "marketplace_db";
//    }
//
//    @Override
//    protected String getContactPoints() {
//        return "localhost";
//    }
//
//    @Override
//    protected int getPort() {
//        return 9042;
//    }
//
//    @Override
//    public SchemaAction getSchemaAction() {
//        return SchemaAction.CREATE_IF_NOT_EXISTS;
//    }
//
//    @Override
//    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
//        CreateKeyspaceSpecification specification = CreateKeyspaceSpecification
//                .createKeyspace(getKeyspaceName())
//                .ifNotExists()
//                .with(KeyspaceOption.DURABLE_WRITES, true)
//                .withSimpleReplication();
//        return Collections.singletonList(specification);
//    }
//}