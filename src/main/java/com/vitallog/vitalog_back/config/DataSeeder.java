package com.vitallog.vitalog_back.config;

import com.vitallog.vitalog_back.domain.entity.*;
import com.vitallog.vitalog_back.domain.enums.InviteStatus;
import com.vitallog.vitalog_back.domain.enums.MemberRole;
import com.vitallog.vitalog_back.domain.enums.NegotiationStatus;
import com.vitallog.vitalog_back.domain.enums.Priority;
import com.vitallog.vitalog_back.domain.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserPreferencesRepository preferencesRepository;
    private final ProductRepository productRepository;
    private final ClientRepository clientRepository;
    private final VitalServiceRepository serviceRepository;
    private final ProductSaleRepository productSaleRepository;
    private final ServiceSaleRepository serviceSaleRepository;
    private final NegotiationRepository negotiationRepository;
    private final CompanyRepository companyRepository;
    private final CompanyMemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("DataSeeder: banco já possui dados, pulando seed.");
            return;
        }

        log.info("DataSeeder: populando banco com dados de teste...");

        /* ── Usuários ── */
        User joao = userRepository.save(User.builder()
                .name("João Silva")
                .email("joao@vitallog.com")
                .password(passwordEncoder.encode("123456"))
                .ativo(true)
                .build());

        User maria = userRepository.save(User.builder()
                .name("Maria Santos")
                .email("maria@vitallog.com")
                .password(passwordEncoder.encode("123456"))
                .ativo(true)
                .build());

        User admin = userRepository.save(User.builder()
                .name("Admin Demo")
                .email("demo@vitallog.com")
                .password(passwordEncoder.encode("demo1234"))
                .ativo(true)
                .build());

        /* ── Preferências ── */
        preferencesRepository.save(UserPreferences.builder().user(joao).currency("R$").theme("dark").notificationsEnabled(true).build());
        preferencesRepository.save(UserPreferences.builder().user(maria).currency("R$").theme("dark").notificationsEnabled(true).build());
        preferencesRepository.save(UserPreferences.builder().user(admin).currency("R$").theme("dark").notificationsEnabled(true).build());

        /* ── Produtos (João) ── */
        Product paracetamol = productRepository.save(Product.builder()
                .name("Paracetamol 500mg").description("Analgésico e antitérmico. Caixa com 20 comprimidos.")
                .quantity(150).price(new BigDecimal("2.50")).user(joao).build());

        Product ibuprofeno = productRepository.save(Product.builder()
                .name("Ibuprofeno 400mg").description("Anti-inflamatório. Caixa com 20 comprimidos.")
                .quantity(89).price(new BigDecimal("4.80")).user(joao).build());

        Product vitaminaC = productRepository.save(Product.builder()
                .name("Vitamina C 1g").description("Suplemento vitamínico efervescente. Tubo com 10 comprimidos.")
                .quantity(200).price(new BigDecimal("9.90")).user(joao).build());

        Product protetorSolar = productRepository.save(Product.builder()
                .name("Protetor Solar FPS 50").description("Protetor solar facial e corporal. 200ml.")
                .quantity(45).price(new BigDecimal("38.00")).user(joao).build());

        Product alcoolGel = productRepository.save(Product.builder()
                .name("Álcool Gel 500ml").description("Higienizador antisséptico 70% INPM.")
                .quantity(78).price(new BigDecimal("13.50")).user(joao).build());

        Product bepantol = productRepository.save(Product.builder()
                .name("Bepantol Derma 50g").description("Creme hidratante e cicatrizante.")
                .quantity(30).price(new BigDecimal("24.90")).user(joao).build());

        /* ── Produtos (Maria) ── */
        productRepository.save(Product.builder()
                .name("Notebook Lenovo IdeaPad").description("Intel Core i5, 8GB RAM, 256GB SSD.")
                .quantity(12).price(new BigDecimal("2899.00")).user(maria).build());

        productRepository.save(Product.builder()
                .name("Mouse Sem Fio Logitech").description("Mouse óptico 1600 DPI, alcance 10m.")
                .quantity(55).price(new BigDecimal("129.90")).user(maria).build());

        productRepository.save(Product.builder()
                .name("Teclado Mecânico RGB").description("Switch blue, layout ABNT2.")
                .quantity(20).price(new BigDecimal("289.00")).user(maria).build());

        /* ── Clientes (João) ── */
        Client carlos = clientRepository.save(Client.builder()
                .name("Carlos Ferreira").email("carlos.ferreira@email.com").phone("(11) 99234-5678")
                .cpf("123.456.789-00").cep("01310-100").street("Av. Paulista, 1234")
                .city("São Paulo").state("SP").user(joao).build());

        Client ana = clientRepository.save(Client.builder()
                .name("Ana Oliveira").email("ana.oliveira@email.com").phone("(21) 98765-4321")
                .cpf("987.654.321-00").cep("20040-020").street("Rua da Assembleia, 10")
                .city("Rio de Janeiro").state("RJ").user(joao).build());

        Client roberto = clientRepository.save(Client.builder()
                .name("Roberto Lima").email("roberto.lima@gmail.com").phone("(31) 97654-3210")
                .cpf("456.789.123-00").cep("30130-110").street("Rua dos Caetés, 321")
                .city("Belo Horizonte").state("MG").user(joao).build());

        Client fernanda = clientRepository.save(Client.builder()
                .name("Fernanda Costa").email("fernanda.costa@empresa.com").phone("(41) 96543-2109")
                .cpf("789.123.456-00").cep("80010-000").street("Rua XV de Novembro, 500")
                .city("Curitiba").state("PR").user(joao).build());

        clientRepository.save(Client.builder()
                .name("Pedro Alves").email("pedro.alves@email.com").phone("(51) 95432-1098")
                .city("Porto Alegre").state("RS").user(joao).build());

        /* ── Clientes (Maria) ── */
        clientRepository.save(Client.builder()
                .name("Tech Solutions LTDA").email("contato@techsolutions.com").phone("(11) 3234-5678")
                .cep("04538-133").street("Rua Leopoldo Couto Magalhães Jr, 700")
                .city("São Paulo").state("SP").user(maria).build());

        clientRepository.save(Client.builder()
                .name("Startup Inovação").email("hello@startup.io").phone("(11) 91234-5678")
                .city("São Paulo").state("SP").user(maria).build());

        /* ── Serviços (João) ── */
        VitalService consultoria = serviceRepository.save(VitalService.builder()
                .name("Consultoria de Estoque").description("Análise e reorganização completa do estoque.")
                .price(new BigDecimal("180.00")).user(joao).build());

        VitalService treinamento = serviceRepository.save(VitalService.builder()
                .name("Treinamento de Equipe").description("Capacitação da equipe em boas práticas de gestão.")
                .price(new BigDecimal("350.00")).user(joao).build());

        serviceRepository.save(VitalService.builder()
                .name("Auditoria Fiscal").description("Revisão de documentos fiscais e conformidade tributária.")
                .price(new BigDecimal("600.00")).user(joao).build());

        /* ── Vendas de Produtos (João) ── */
        LocalDateTime ontem = LocalDateTime.now().minusDays(1);
        LocalDateTime semanaPassada = LocalDateTime.now().minusDays(7);
        LocalDateTime doisDiasAtras = LocalDateTime.now().minusDays(2);

        productSaleRepository.save(ProductSale.builder()
                .user(joao).product(paracetamol).quantity(5).client("Carlos Ferreira")
                .pricePerUnit(paracetamol.getPrice()).total(paracetamol.getPrice().multiply(new BigDecimal("5")))
                .status("pago").createdAt(semanaPassada).build());

        productSaleRepository.save(ProductSale.builder()
                .user(joao).product(vitaminaC).quantity(3).client("Ana Oliveira")
                .pricePerUnit(vitaminaC.getPrice()).total(vitaminaC.getPrice().multiply(new BigDecimal("3")))
                .status("pago").createdAt(semanaPassada.plusDays(2)).build());

        productSaleRepository.save(ProductSale.builder()
                .user(joao).product(protetorSolar).quantity(2).client("Roberto Lima")
                .pricePerUnit(protetorSolar.getPrice()).total(protetorSolar.getPrice().multiply(new BigDecimal("2")))
                .status("pago").createdAt(doisDiasAtras).build());

        productSaleRepository.save(ProductSale.builder()
                .user(joao).product(ibuprofeno).quantity(10).client("Fernanda Costa")
                .pricePerUnit(ibuprofeno.getPrice()).total(ibuprofeno.getPrice().multiply(new BigDecimal("10")))
                .status("pago").createdAt(ontem).build());

        productSaleRepository.save(ProductSale.builder()
                .user(joao).product(alcoolGel).quantity(4).client("Consumidor Final")
                .pricePerUnit(alcoolGel.getPrice()).total(alcoolGel.getPrice().multiply(new BigDecimal("4")))
                .status("pendente").createdAt(LocalDateTime.now()).build());

        productSaleRepository.save(ProductSale.builder()
                .user(joao).product(bepantol).quantity(2).client("Carlos Ferreira")
                .pricePerUnit(bepantol.getPrice()).total(bepantol.getPrice().multiply(new BigDecimal("2")))
                .status("pago").createdAt(LocalDateTime.now().minusDays(3)).build());

        /* ── Vendas de Serviços (João) ── */
        serviceSaleRepository.save(ServiceSale.builder()
                .user(joao).service(consultoria).quantity(1).client("Carlos Ferreira")
                .pricePerUnit(consultoria.getPrice()).total(consultoria.getPrice())
                .createdAt(semanaPassada).build());

        serviceSaleRepository.save(ServiceSale.builder()
                .user(joao).service(treinamento).quantity(1).client("Ana Oliveira")
                .pricePerUnit(treinamento.getPrice()).total(treinamento.getPrice())
                .createdAt(doisDiasAtras).build());

        /* ── Negociações (João) ── */
        negotiationRepository.save(Negotiation.builder()
                .user(joao).client("Distribuidora MedFarma")
                .description("Contrato de fornecimento mensal de medicamentos OTC para rede de farmácias.")
                .estimatedValue(new BigDecimal("12500.00"))
                .attendance("Reunião realizada 14/05. Cliente solicitou proposta formal.")
                .status(NegotiationStatus.EM_CRIACAO).priority(Priority.ALTA)
                .createdAt(LocalDateTime.now().minusDays(5)).build());

        negotiationRepository.save(Negotiation.builder()
                .user(joao).client("Clínica Saúde Total")
                .description("Fornecimento de produtos de higiene e proteção para clínica médica.")
                .estimatedValue(new BigDecimal("4200.00"))
                .attendance("Proposta enviada por e-mail. Aguardando retorno do gestor de compras.")
                .status(NegotiationStatus.EM_CRIACAO).priority(Priority.MEDIA)
                .createdAt(LocalDateTime.now().minusDays(3)).build());

        negotiationRepository.save(Negotiation.builder()
                .user(joao).client("Prefeitura Municipal")
                .description("Licitação para fornecimento de kits de primeiros socorros.")
                .estimatedValue(new BigDecimal("31000.00"))
                .attendance("Documentação entregue. Edital nº 2024/089. Resultado em 30 dias.")
                .nf("NF-0042").status(NegotiationStatus.FATURADO).priority(Priority.ALTA)
                .createdAt(LocalDateTime.now().minusDays(15))
                .invoicedAt(LocalDateTime.now().minusDays(2)).build());

        /* ── Empresa + Membros ── */
        Company empresa = companyRepository.save(Company.builder()
                .name("VitalLog Saúde LTDA")
                .description("Distribuidora de produtos farmacêuticos e de higiene pessoal.")
                .owner(joao).build());

        memberRepository.save(CompanyMember.builder()
                .company(empresa).user(joao).role(MemberRole.OWNER).build());

        memberRepository.save(CompanyMember.builder()
                .company(empresa).user(maria).role(MemberRole.MEMBER).build());

        log.info("DataSeeder: seed concluído com sucesso! Usuários criados:");
        log.info("  → joao@vitallog.com / 123456");
        log.info("  → maria@vitallog.com / 123456");
        log.info("  → demo@vitallog.com / demo1234");
    }
}
