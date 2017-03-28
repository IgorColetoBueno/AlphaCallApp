package alphacallapp.com.br.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Igor Bueno on 16/01/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "AlphaCallApp";
    private static final int VERSAO = 1;

    public DatabaseHelper(Context context) {
        super(context, BANCO_DADOS, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //cria usuários


        //CRIA A TABELA DE USUÁRIOS
        db.execSQL("CREATE TABLE ANDROID_USUARIOS(ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, NUSUARIO VARCHAR(15), PASSWORD VARCHAR(15));");

        //CRIA A TABELA TIPO DE ATENDIMENTO
        db.execSQL("CREATE TABLE ANDROID_TATENDIMENTO (ID_TATENDIMENTO INTEGER PRIMARY KEY AUTOINCREMENT, NATENDIMENTO VARCHAR(30)," +
                " EIMPLANTACAO INT);");

        //CRIA A TABELA DE CLIENTE
        db.execSQL("CREATE TABLE ANDROID_CLIENTE (ID_CLIENTE INTEGER PRIMARY KEY AUTOINCREMENT,  NCLIENTE VARCHAR(60)," +
                " CIDADE VARCHAR(50), UF VARCHAR(2));");

        //CRIA A TABELA DE MÓDULO
        db.execSQL("CREATE TABLE ANDROID_MODULO(ID_MODULO INTEGER PRIMARY KEY AUTOINCREMENT, NMODULO VARCHAR(50));");
        //CRIA A TABELA DE SUBMÓDULO
        db.execSQL("CREATE TABLE ANDROID_SUBMODULO(ID_SUBMODULO INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ID_MODULO INT NOT NULL REFERENCES ANDROID_MODULO ON DELETE NO ACTION ON UPDATE NO ACTION, " +
                " NSUBMODULO VARCHAR(50));");

        //CRIA A TABELA DE FUNCOES
        db.execSQL("CREATE TABLE ANDROID_FUNCOES(ID_FUNCAO INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    ID_MODULO INT NOT NULL REFERENCES ANDROID_MODULO(ID_MODULO) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                "    ID_SUBMODULO INT NOT NULL REFERENCES ANDROID_SUBMODULO(ID_SUBMODULO) ON DELETE NO ACTION ON UPDATE NO ACTION, " +
                "    NFUNCAO VARCHAR(60));");

        //CRIA A TABELA DE ATENDIMENTOS
        db.execSQL("CREATE TABLE ANDROID_ATENDIMENTO(NUMERO INTEGER PRIMARY KEY AUTOINCREMENT," +
                "    ID_CLIENTE_ATD INT NOT NULL REFERENCES ANDROID_CLIENTE ON DELETE NO ACTION ON UPDATE NO ACTION," +
                "    NUSUARIO VARCHAR(20), DESCRICAO VARCHAR(30), DATA_AGD DATE, HORA_AGD TIME," +
                "    ID_MODULO_ATD INT NOT NULL REFERENCES ANDROID_MODULO ON DELETE NO ACTION ON UPDATE NO ACTION," +
                "    PARECER VARCHAR(1000), DATA_CON DATE, HORA_CON TIME, OBS VARCHAR(1000), SITUACAO VARCHAR(20)," +
                "    ID_SUBMODULO_ATD INT NOT NULL REFERENCES ANDROID_SUBMODULO ON DELETE NO ACTION ON UPDATE NO ACTION," +
                "    ID_FUNCAO_ATD INT NOT NULL REFERENCES ANDROID_FUNCOES ON DELETE NO ACTION ON UPDATE NO ACTION," +
                "    ID_USUARIO INT NOT NULL REFERENCES ANDROID_USUARIOS ON DELETE NO ACTION ON UPDATE NO ACTION," +
                "    ID_TATENDIMENTO INT NOT NULL REFERENCES ANDROID_TATENDIMENTO ON DELETE NO ACTION ON UPDATE NO ACTION);");

        db.execSQL("INSERT INTO ANDROID_USUARIOS (NUSUARIO, PASSWORD) VALUES ('master','nn302250');");

        db.execSQL("INSERT INTO ANDROID_TATENDIMENTO (NATENDIMENTO,EIMPLANTACAO) VALUES('Treinamento', 0),('Implantação', 1),('Suporte', 0);");

        db.execSQL("INSERT INTO ANDROID_CLIENTE (NCLIENTE,CIDADE,UF) VALUES('Transportadora Giomila','Vilhena','RO');");

        db.execSQL("INSERT INTO ANDROID_CLIENTE (NCLIENTE,CIDADE,UF) VALUES('Transportadora Giomila - MT','Cáceres','MT');");

        db.execSQL("INSERT INTO ANDROID_CLIENTE (NCLIENTE,CIDADE,UF) VALUES('TRR Krupinski','Vilhena','RO');");

        db.execSQL("INSERT INTO ANDROID_CLIENTE (NCLIENTE,CIDADE,UF) VALUES('Hiperhaus','Vilhena','RO');");

        db.execSQL("INSERT INTO ANDROID_MODULO (NMODULO) VALUES ('Todos os módulos'),('Alpha Software'),('Cadastros'),('Configurações'),('Consultas')," +
                "('Controle de Acesso'),('Controle de Resíduos'),('Controle de passagens'),('Controle de veículos'),('Estoque')," +
                "('Faturamento'),('Financeiro'),('Fiscal'),('Relatórios');");
        /*Cadastros de submódulos*/

        //Todos
        db.execSQL("INSERT INTO ANDROID_SUBMODULO VALUES(1,1,'Todos os sub módulos')");
        //Alpha Software
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES (2,'Todos de Alpha Software'),(2,'Gerar Mensalidade'),(2,'Gerar NFS-e')," +
                "(2,'Entrega de boletos'),(2,'Todos'),(2,'Liberação Online'),(2,'Boletos Online'),(2,'Reajuste de contratos'),(2,'Ramo de atividade')" +
                ",(2,'Categorias de suporte'),(2,'Atendimento telefônico'),(2,'Gestor de tarefas'),(2,'Versão do Alpha Express'),(2,'Consulta de cliente')" +
                ",(2,'Exportar cadastro'),(2,'ChangeLog'),(2,'Relatórios');");
        //Cadastros
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES (3,'Todos de cadastros'),(3,'Empresa'),(3,'Cidades'),(3,'Empresa'),(3,'Regiões')," +
                "(3,'Empresa'),(3,'Tipo de pessoas'),(3,'Clientes'),(3,'Fornecedores'),(3,'Função de funcionários'),(3,'Funcionários')," +
                "(3,'Produtos'),(3,'Tipo de documentos'),(3,'Formas de pagamento'),(3,'Fornecedores'),(3,'Adm. Cartões'),(3,'Bancos')," +
                "(3,'Contas bancárias'),(3,'Históricos bancários'),(3,'Plano de contas'),(3,'Centros de custo'),(3,'Fornecedores')," +
                "(3,'Natureza de operação'),(3,'Transportadoras'),(3,'Natureza de operação CT-E'),(3,'Cadastros');");
        //Configurações
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES(4,'Todos de configurações'),(4,'Configurações de terminal')," +
                "(4,'Configurações do sistema')");
        //Consultas
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES (5,'Todos de consultas'),(5,'Produtos/Serviços')," +
                "(5,'Clientes'),(5,'Conjuntos de produtos'),(5,'Veículos'),(5,'Aniversariantes');");
        //Controle de Acesso
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES(6,'Todos de controle de acesso'),(6,'Grupos de acesso'),(6,'Usuários')," +
                "(6,'Alterar senha'),(6,'Efetuar logoff')");
        //Controle de Resíduos
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES(7,'Todos de controle de resíduos')");
        //Controle de passagens
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES(8,'Todos de controle de passagens')");
        //Controle de veículos
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES(9,'Todos de controle de veículos'),(9,'Acertos de viagens'),(9,'Fretes')" +
                ",(9,'Carta fretes'),(9,'Ordem de serviço - prestação'),(9,'Abastecimentos'),(9,'Ordem de Serviço')," +
                "(9,'Controle de pneus'),(9,'Controle de CT-e'),(9,'Controle de MDF-e'),(9,'Despesas/Receitas de veículos')" +
                ",(9,'Produtividade de veículos'),(9,'Controle de MDF-e'),(9,'Análise por veículo'),(9,'Agrupamentos de veículos')," +
                "(9,'Agendamentos'),(9,'Controle de MDF-e'),(9,'KM de veículos'),(9,'Manutenções preventivas'),(9,'Check List');");
        //Estoque
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES(10,'Todos de estoque'),(10,'Ajuste de estoque e preços')" +
                ",(10,'Ajuste de NCM/Preços'),(10,'Ajustes por grupo/marca'),(10,'Atualizar tributos'),(10,'Histórico de produto/serviço')" +
                ",(10,'Fornecimento de produtos'),(10,'Zerar estoque'),(10,'Sugestão de compras'),(10,'Movimentação de produtos')" +
                ",(10,'Todos'),(10,'Exportar NCM'),(10,'Importar NCM'),(10,'Exportar p/ balança'),(10,'Romaneio de carregamento');");
        //Faturamento
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES(11,'Todos de faturamento'),(11,'Compras'),(11,'PDV'),(11,'NFS-e')" +
                ",(11,'NFC-e'),(11,'NF-e'),(11,'Caixa diário'),(11,'Caixa cofre'),(11,'Liberação de processos'),(11,'Operações fechadas')" +
                ",(11,'Consultar por descrição'),(11,'Indicadores de operação'),(11,'Lucro diário de operações'),(11,'Gerar arquivo de remessa')" +
                ",(11,'Ler arquivo de retorno'),(11,'Todos'),(11,'Configurar diretórios');");
        //Financeiro
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES(12,'Todos de financeiro'),(12,'Contas a receber'),(12,'SPC Brasil')" +
                ",(12,'Verificar devedores'),(12,'Controle de cartão'),(12,'Pesquisar, alterar, reimprimir e baixar boletos')" +
                ",(12,'Gerar arquivo de remessa'),(12,'Ler arquivo de retorno'),(12,'Folha de pagamento'),(12,'Gerar saldo a pagar')" +
                ",(12,'Comissões'),(12,'Lançar débitos/créditos'),(12,'Contas a pagar'),(12,'Livro caixa'),(12,'Conta corrente')" +
                ",(12,'Recibo avulso'),(12,'Agenda financeira'),(12,'Configuração para boletos');");
        //Fiscal
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES(13,'Todos de fiscal'),(13,'Controle de notas'),(13,'Notas de consumo')" +
                ",(13,'Conhecimento de transportes'),(13,'Consultar NF-e na SEFAZ'),(13,'Gerar Sped');");
        //Relatórios
        db.execSQL("INSERT INTO ANDROID_SUBMODULO (ID_MODULO,NSUBMODULO) VALUES(14,'Todos de relatórios'),(14,'Editar relatórios'),(14,'Cadastros')" +
                ",(14,'Etiquetas'),(14,'Estoque'),(14,'Balanço'),(14,'Tabela de preços'),(14,'Vendas, orçamentos e ordem de serviço')" +
                ",(14,'Compras'),(14,'Frotas'),(14,'Contas a receber'),(14,'Contas a pagar'),(14,'Livro caixa'),(14,'Conta corrente')" +
                ",(14,'Folha de pagamento'),(14,'NF-e'),(14,'NFC-e'),(14,'NFS-e');");

        //Todos
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (1,1,'Todas as funções')");
        //Alpha Software
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (2,2,'Todas as funções de Alpha Software')");
        //Cadastros
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (3,19,'Todas as funções de cadastros')");
        //Configurações
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (4,45,'Todas as funções de configurações')");
        //Consultas
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (5,48,'Todas as funções de consultas')");
        //Controle de acesso
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (6,54,'Todas as funções de controle de acesso')");
        //Controle de resíduos
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (7,59,'Todas as funções de controle de resíduos')");
        //Controle de passagens
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (8,60,'Todas as funções de controle de passagens')");
        //Controle de veículos
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (9,61,'Todas as funções de controle de veículos')");
        //Estoque
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (10,81,'Todas as funções de estoque')");
        //Faturamento
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (11,96,'Todas as funções de faturamento')");
        //Finaceiro
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (12,113,'Todas as funções de financeiro')");
        //Fiscal
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (13,131,'Todas as funções de fiscal')");
        //Relatóriosx
        db.execSQL("INSERT INTO ANDROID_FUNCOES(ID_MODULO,ID_SUBMODULO,NFUNCAO) VALUES (14,137,'Todas as funções de relatórios')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static class Usuario {
        public static final String TABELA = "ANDROID_USUARIOS";

        public static final String ID_USUARIO = "ID_USUARIO";

        public static final String NUSUARIO = "NUSUARIO";

        public static final String PASSWORD = "PASSWORD";

        public static final String[] COLUNAS_USUARIO = new String[]{
                ID_USUARIO, NUSUARIO, PASSWORD
        };
    }

    public static class Cliente {
        public static final String TABELA = "ANDROID_CLIENTE";

        public static final String ID_CLIENTE = "ID_CLIENTE";

        public static final String NCLIENTE = "NCLIENTE";

        public static final String CIDADE = "CIDADE";

        public static final String UF = "UF";

        public static final String[] COLUNAS_CLIENTE = new String[]{
                ID_CLIENTE, NCLIENTE, CIDADE, UF
        };
    }

    public static class Modulo {
        public static final String TABELA = "ANDROID_MODULO";

        public static final String ID_MODULO = "ID_MODULO";

        public static final String NMODULO = "NMODULO";

        public static final String[] COLUNAS_MODULO = new String[]{
                ID_MODULO, NMODULO
        };
    }

    public static class SubModulo {
        public static final String TABELA = "ANDROID_SUBMODULO";

        public static final String ID_SUBMODULO = "ID_SUBMODULO";

        public static final String ID_MODULO = "ID_MODULO";

        public static final String NSUBMODULO = "NSUBMODULO";

        public static final String[] COLUNAS_SUBMODULO = new String[]{
                ID_SUBMODULO, ID_MODULO, NSUBMODULO
        };
    }

    public static class Funcao {
        public static final String TABELA = "ANDROID_FUNCOES";

        public static final String ID_FUNCAO = "ID_FUNCAO";

        public static final String ID_MODULO = "ID_MODULO";

        public static final String ID_SUBMODULO = "ID_SUBMODULO";

        public static final String NFUNCAO = "NFUNCAO";

        public static final String[] COLUNAS_FUNCAO = new String[]{
                ID_FUNCAO, ID_MODULO, ID_SUBMODULO,NFUNCAO
        };
    }

    public static class TAtendimento {
        public static final String TABELA = "ANDROID_TATENDIMENTO";

        public static final String ID_TATENDIMENTO = "ID_TATENDIMENTO";

        public static final String NATENDIMENTO = "NATENDIMENTO";

        public static final String EIMPLANTACAO = "EIMPLANTACAO";

        public static final String COLUNAS_TATENDIMENTO[] = new String[]{
                ID_TATENDIMENTO, NATENDIMENTO, EIMPLANTACAO
        };

    }

    public static class Atendimentos {

        public static final String TABELA = "ANDROID_ATENDIMENTO";

        public static final String NUMERO = "NUMERO";

        public static final String ID_CLIENTE_ATD = "ID_CLIENTE_ATD";

        public static final String NUSUARIO = "NUSUARIO";

        public static final String DESCRICAO = "DESCRICAO";

        public static final String DATA_AGD = "DATA_AGD";

        public static final String HORA_AGD = "HORA_AGD";

        public static final String ID_MODULO_ATD = "ID_MODULO_ATD";

        public static final String PARECER = "PARECER";

        public static final String DATA_CON = "DATA_CON";

        public static final String HORA_CON = "HORA_CON";

        public static final String OBS = "OBS";

        public static final String SITUACAO = "SITUACAO";

        public static final String ID_SUBMODULO_ATD = "ID_SUBMODULO_ATD";

        public static final String ID_FUNCAO_ATD = "ID_FUNCAO_ATD";

        public static final String ID_USUARIO = "ID_USUARIO";

        public static final String ID_TATENDIMENTO = "ID_TATENDIMENTO";

        public static final String[] COLUNAS_ATENDIMENTOS = new String[]{
                NUMERO, ID_CLIENTE_ATD, NUSUARIO, DESCRICAO,
                DATA_AGD, HORA_AGD, ID_MODULO_ATD, PARECER,
                DATA_CON, HORA_CON, OBS, SITUACAO, ID_SUBMODULO_ATD, ID_FUNCAO_ATD,
                ID_USUARIO, ID_TATENDIMENTO
        };
    }

}
