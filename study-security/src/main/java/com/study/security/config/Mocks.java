package com.study.security.config;

import com.study.security.model.Beneficiary;
import com.study.security.model.Document;
import com.study.security.model.User;
import com.study.security.repository.BeneficiaryRepository;
import com.study.security.repository.DocumentRepository;
import com.study.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Arrays;

@Configuration
public class Mocks implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BeneficiaryRepository beneficiaryRepository;

    @Autowired
    DocumentRepository documentRepository;

    @Override
    public void run(String... args) throws Exception {

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pass = passwordEncoder.encode("123456");
        User user = new User(null, "username", pass);

        Beneficiary beneficiary1 = new Beneficiary();
        beneficiary1.setName("Fulano de Tal");
        beneficiary1.setPhone("11912344321");
        beneficiary1.setBirthday(LocalDate.now());
        beneficiary1.setInclusionDate(LocalDate.now());
        beneficiary1.setUpdateDate(LocalDate.now());

        Beneficiary beneficiary2 = new Beneficiary();
        beneficiary2.setName("Sicrano da Silva");
        beneficiary2.setPhone("11912345678");
        beneficiary2.setBirthday(LocalDate.now());

        Document document1 = new Document();
        document1.setDocumentType("CPF");
        document1.setDescription("Certidão de Pessoa Fisica");
        document1.setInclusionDate(LocalDate.now());
        document1.setUpdateDate(LocalDate.now());
        document1.setBeneficiary(beneficiary1);

        Document document2 = new Document();
        document2.setDocumentType("RG");
        document2.setDescription("Registro Geral");
        document2.setInclusionDate(LocalDate.now());
        document2.setBeneficiary(beneficiary1);

        Document document3 = new Document();
        document3.setDocumentType("RNE");
        document3.setDescription("Registro Nacional de Estrangeiro");
        document3.setInclusionDate(LocalDate.now());
        document3.setBeneficiary(beneficiary2);

        userRepository.save(user);
        beneficiaryRepository.saveAll(Arrays.asList(beneficiary1, beneficiary2));
        documentRepository.saveAll(Arrays.asList(document1, document2, document3));
    }
}
