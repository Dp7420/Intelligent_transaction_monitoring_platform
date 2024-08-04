//package com.www.demo.app.itsmdemo.service;
//
//import java.util.Map;
//
//import org.apache.kafka.common.serialization.Serializer;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.www.demo.app.itsmdemo.entity.Transaction;
//
//public class TransactionSerializer implements Serializer<Transaction> {
//	private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Override
//    public void configure(Map<String, ?> configs, boolean isKey) {
//        // Configuration if needed
//    }
//
//    @Override
//    public byte[] serialize(String topic, Transaction data) {
//        try {
//            return objectMapper.writeValueAsBytes(data);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException("Error serializing Transaction", e);
//        }
//    }
//
//    @Override
//    public void close() {
//        // Cleanup if needed
//    }
//}