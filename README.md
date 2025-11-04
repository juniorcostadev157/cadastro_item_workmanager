🚀 Projeto MVVM Clean XML + Firebase – Versão Avançada (com Hilt, Testes e Sincronização Manual)

🧩 Descrição

Versão evoluída do projeto anterior, criada para demonstrar práticas profissionais de arquitetura, testes e injeção de dependência.
O app agora implementa Hilt, testes unitários e instrumentados (UI) e sincronização controlada manualmente pelo usuário.

O foco dessa atualização foi levar a estrutura a um padrão de produção — com código modular, altamente testável e manutenção simplificada.

🧱 Principais Diferenças em Relação à Versão Anterior
Recurso	Antes	Agora
Injeção de dependência	Nenhuma (manual)	✅ Hilt (Google Dagger)
Testes	Apenas unitários simples	✅ JUnit + MockK + Espresso (UI)
Sincronização	Sempre automática	✅ Botão de ativação/desativação de sincronização
Arquitetura	MVVM + Clean Architecture	✅ MVVM + Clean Architecture + DI Hilt
Repositórios	Criados manualmente	✅ Injetados com @Inject via Hilt
Testes de UI	—	✅ Espresso + FragmentScenario (UI Testing)
Cobertura	Jacoco básico	✅ Jacoco + Relatórios detalhados
🧩 Arquitetura

Segue os princípios de Clean Architecture e MVVM, com divisão clara entre camadas:

data/        → Fontes de dados (Room + Firestore) e Repositórios
domain/      → Casos de uso e modelos de domínio
presentation/→ ViewModels, Fragments e Adapters
di/          → Módulos de Injeção do Hilt
worker/      → Sincronização automática (WorkManager)
core/        → Utils, Extensions e Loggers

⚙️ Tecnologias Principais
Categoria	Ferramenta / Biblioteca
Linguagem	Kotlin
UI	XML + Material Design
Arquitetura	MVVM + Clean Architecture + Hilt (DI)
Reatividade	LiveData + Kotlin Flow
Banco Local	Room Database
Backend	Firebase Firestore
Autenticação	Firebase Auth
Sincronização	WorkManager (Offline-first)
Controle de Sincronização	Botão manual para ativar/desativar sync
Testes Unitários	JUnit + MockK + Coroutines Test
Testes de UI	Espresso + FragmentScenario
Cobertura	Jacoco (HTML/XML Report)
Monitoramento	Firebase Analytics + Crashlytics
CI/CD	GitHub Actions (build, test e deploy automáticos)
🧠 Funcionalidades

✅ Login e cadastro de usuários
✅ Criação e listagem de itens (Room + Firestore)
✅ Sincronização manual ou automática via botão toggle
✅ Feedback visual com ProgressBar e status de sincronização
✅ Persistência offline (offline-first)
✅ Métricas e erros monitorados no Firebase
✅ Testes unitários e de UI cobrindo lógicas e interações
✅ CI/CD completo com build, testes e deploy automatizado

🧪 Testes Automatizados
🔹 Unit Tests

Testes da camada de lógica com:

JUnit

MockK para mocks de dependências

CoroutinesTest para flows e LiveData

Jacoco para relatórios de cobertura

🔹 UI Tests (Instrumented)

Testes da camada de interface com:

Espresso e FragmentScenario

Verificação de elementos (isDisplayed(), perform(click()))

Simulação de interações (toasts, loading, recyclerView)

🔄 CI/CD Pipeline

Rodando no GitHub Actions, com duas etapas principais:

🧪 CI – Continuous Integration

Análise estática: Detekt + Ktlint

Lint Android

Testes unitários e instrumentados

Relatórios Jacoco automáticos

🚀 CD – Continuous Deployment

Build release com keystore via secrets

Upload do APK como artifact

Deploy automático no Firebase App Distribution

Release notes geradas automaticamente

⚙️ Como Rodar Localmente

Clone este repositório

Adicione o google-services.json em /app

(Opcional) Configure o local.properties com o keystore

Execute:

./gradlew assembleDebug

🧑‍💻 Propósito da Atualização

Essa versão foi criada para demonstrar como aplicar injeção de dependência com Hilt, testes automatizados e melhor controle de sincronização em um projeto Android real.
Ela serve como comparativo direto com a versão anterior, mostrando a diferença em escalabilidade, clareza e testabilidade do código.

📊 Monitoramento e Testes

Firebase Analytics: métricas de uso e eventos personalizados

Crashlytics: captura automática de falhas

Jacoco: relatório completo de cobertura

Espresso & MockK: validação de UI e lógicas de negócio

✉️ Contato

👤 Junior Costa
📧 juniorcosta15785@gmail.com

💼 LinkedIn

💻 Portfólio