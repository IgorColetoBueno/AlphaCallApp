package alphacallapp.com.br.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.html.ParaGraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;

import alphacallapp.com.br.DAO.AtendimentosDAO;
import alphacallapp.com.br.DAO.ClienteDAO;
import alphacallapp.com.br.DAO.FuncaoDAO;
import alphacallapp.com.br.DAO.ModuloDAO;
import alphacallapp.com.br.DAO.SubModuloDAO;
import alphacallapp.com.br.DAO.TAtendimentoDAO;
import alphacallapp.com.br.DAO.UsuarioDAO;
import alphacallapp.com.br.model.Atendimentos;
import alphacallapp.com.br.model.Cliente;

/*
 * Created by Igor Bueno on 21/02/2017.
 */

public class GeradorPDF {

    Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD);

    Font fontNegrito = FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD);

    Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 10);

    Font fontIndice = FontFactory.getFont(FontFactory.HELVETICA, 8, Font.BOLD);

    Font fontData = FontFactory.getFont(FontFactory.HELVETICA, 8);


    private static final String NOME_APP = "alphacallapp.com.br";

    private static final String GERADOS = "MeusArquivos";

    public void GerarPDF(Context context, Atendimentos att, Intent emailIntent) {

        Document document = new Document(PageSize.A4);
        String NOME_ARQUIVO = "Relatório-atendimento-ID-" + att.getNumero() + "-"+".pdf";
        String trajetoSD = Environment.getExternalStorageDirectory().toString();
        File pdfDir = new File(trajetoSD + File.separator + NOME_APP);

        if (!pdfDir.exists()) {
            pdfDir.mkdir();
        }

        File pdfSubDir = new File(pdfDir.getPath() + File.separator + GERADOS);

        if (!pdfSubDir.exists()) {
            pdfSubDir.mkdir();

        }

        String nome_completo = Environment.getExternalStorageDirectory() + File.separator + NOME_APP + File.separator +
                GERADOS + File.separator + NOME_ARQUIVO;

        File outputfile = new File(nome_completo);

        try {
            String[] mailto = {"igor@alphasoftware.com.br"};
            Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory() + File.separator + NOME_APP + File.separator +
                    GERADOS + File.separator + NOME_ARQUIVO));

            emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.putExtra(Intent.EXTRA_EMAIL, mailto);
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Calc PDF Report");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Hi PDF is attached in this mail. ");
            emailIntent.setType("application/pdf");
            emailIntent.putExtra(Intent.EXTRA_STREAM, uri);
            Toast.makeText(context, "Enviado com sucesso!", Toast.LENGTH_SHORT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        if (outputfile.exists()) {

            outputfile.delete();

        }

        try {
            //Classe gerador de PDF
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(nome_completo));
            //Inserção de título no relatório
            Paragraph titulo = new Paragraph("Relatório de Atendimento", fontTitle);
            titulo.setAlignment(Element.ALIGN_CENTER);
            titulo.setSpacingAfter(30);
            //Classe geradora de tabelas
            PdfPTable table1 = new PdfPTable(new float[]{0.125f, 0.13f, 0.15f, 0.2f, 0.15f, 0.25f});
            PdfPTable table2 = new PdfPTable(new float[]{0.125f, 0.28f, 0.1f, 0.1f, 0.15f, 0.25f});
            PdfPTable table3 = new PdfPTable(new float[]{0.125f, 0.88f});
            PdfPTable table4 = new PdfPTable(new float[]{0.125f, 0.88f});
            PdfPTable table5 = new PdfPTable(new float[]{0.125f, 0.88f});
            PdfPTable table6 = new PdfPTable(new float[]{0.1245f, 0.2083f, 0.125f, 0.2083f, 0.125f, 0.2083f});
            PdfPTable table7 = new PdfPTable(new float[]{0.1245f, 0.2083f, 0.125f, 0.2083f, 0.125f, 0.2083f});

            table1.setHorizontalAlignment(Element.ALIGN_CENTER);
            table2.setHorizontalAlignment(Element.ALIGN_CENTER);
            table3.setHorizontalAlignment(Element.ALIGN_CENTER);
            table4.setHorizontalAlignment(Element.ALIGN_CENTER);
            table5.setHorizontalAlignment(Element.ALIGN_CENTER);
            table6.setHorizontalAlignment(Element.ALIGN_CENTER);
            table7.setHorizontalAlignment(Element.ALIGN_CENTER);

            table1.setWidthPercentage(85.0f);
            table2.setWidthPercentage(85.0f);
            table3.setWidthPercentage(85.0f);
            table4.setWidthPercentage(85.0f);
            table5.setWidthPercentage(85.0f);
            table6.setWidthPercentage(85.0f);
            table7.setWidthPercentage(85.0f);
            //cabeçalho da tabela - Adiciona células da esquerda para a direita e ao chegar no fim cria uma nova linha
            AtendimentosDAO attdao = new AtendimentosDAO(context);
            ClienteDAO clienteDAO = new ClienteDAO(context);
            ModuloDAO moduloDAO = new ModuloDAO(context);
            SubModuloDAO subModuloDAO = new SubModuloDAO(context);
            FuncaoDAO funcaoDAO = new FuncaoDAO(context);
            UsuarioDAO usuarioDAO = new UsuarioDAO(context);
            TAtendimentoDAO tAtendimentosDAO = new TAtendimentoDAO(context);
            //primeira linha
            table1.addCell(new Phrase("ID:", fontNegrito));
            table1.addCell(new Phrase(Integer.toString(att.getNumero()), fontNormal));
            table1.addCell(new Phrase("Data inicial:", fontNegrito));
            table1.addCell(new Phrase(att.getData_agd(), fontNormal));
            table1.addCell(new Phrase("Hora inicial:", fontNegrito));
            table1.addCell(new Phrase(att.getHora_agd(), fontNormal));

            //segunda linha
            table1.addCell(new Phrase("Técnico:", fontNegrito));
            table1.addCell(new Phrase(att.getNusuario(), fontNormal));
            table1.addCell(new Phrase("Data final:", fontNegrito));
            table1.addCell(new Phrase(att.getData_con(), fontNormal));
            table1.addCell(new Phrase("Hora final:", fontNegrito));
            table1.addCell(new Phrase(att.getHora_con(), fontNormal));

            Cliente cli = clienteDAO.ConsultarId(att.getId_cliente_atd());
            //terceira linha
            table2.addCell(new Phrase("Cliente:", fontNegrito));
            table2.addCell(new Phrase(cli.getNome_cliente(), fontNormal));
            table2.addCell(new Phrase("UF:", fontNegrito));
            table2.addCell(new Phrase(cli.getUf(), fontNormal));
            table2.addCell(new Phrase("Cidade:", fontNegrito));
            table2.addCell(new Phrase(cli.getCidade_cliente(), fontNormal));

            //quarta linha
            table3.addCell(new Phrase("Descrição:", fontNegrito));
            table3.addCell(new Phrase(att.getDescricao(), fontNormal));

            //quinta linha
            table4.addCell(new Phrase("Parecer:", fontNegrito));
            table4.addCell(new Phrase(att.getParecer(), fontNormal));

            //sexta linha
            table5.addCell(new Phrase("Obs:", fontNegrito));
            table5.addCell(new Phrase(att.getObs(), fontNormal));

            //sétima linha
            table6.addCell(new Phrase("Módulo:", fontNegrito));
            table6.addCell(new Phrase(moduloDAO.ConsultarModuloId(att.getId_modulo_atd()), fontNormal));
            table6.addCell(new Phrase("Sub mód:", fontNegrito));
            table6.addCell(new Phrase(subModuloDAO.ConsultarSubmoduloId(att.getId_submodulo_atd()), fontNormal));
            table6.addCell(new Phrase("Função:", fontNegrito));
            table6.addCell(new Phrase(funcaoDAO.ConsultarModuloId(att.getId_funcao_atd()), fontNormal));

            String dataAtual;
            long date = System.currentTimeMillis();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - hh:MM");
            dataAtual = sdf.format(date);
            //sétima linha
            table7.addCell(new Phrase("Criador:", fontNegrito));
            table7.addCell(new Phrase(usuarioDAO.ConsultarUsuarioId(att.getId_usuario()), fontNormal));
            table7.addCell(new Phrase("Situação:", fontNegrito));
            table7.addCell(new Phrase(att.getSituacao(), fontNormal));
            table7.addCell(new Phrase("Gerado em:", fontIndice));
            table7.addCell(new Phrase(dataAtual, fontData));

            //Cria campo de assinatura
            Paragraph assinatura1 = new Paragraph("_____________________________", fontNormal);
            assinatura1.setAlignment(Element.ALIGN_LEFT);
            assinatura1.setIndentationLeft(40);
            assinatura1.setSpacingBefore(60);
            Paragraph descricao1 = new Paragraph("Representante do suporte", fontNegrito);
            descricao1.setAlignment(Element.ALIGN_LEFT);
            descricao1.setIndentationLeft(60);

            Paragraph assinatura2 = new Paragraph("_____________________________", fontNormal);
            assinatura2.setAlignment(Element.ALIGN_RIGHT);
            assinatura2.setIndentationRight(40);
            assinatura2.setSpacingBefore(-27);
            Paragraph descricao2 = new Paragraph("Cliente", fontNegrito);
            descricao2.setAlignment(Element.ALIGN_RIGHT);
            descricao2.setIndentationRight(100);
            //Abre o documento criado
            document.open();
            //Adiciona informações gerais sobre o mesmo
            document.addAuthor("Igor Bueno");
            document.addCreator("Igor Bueno");
            document.addSubject("Aqui consta o conteúdo do relatório");
            document.addCreationDate();
            document.addTitle("Relatório de Atendimento");
            document.add(titulo);
            document.add(table1);
            document.add(table2);
            document.add(table3);
            document.add(table4);
            document.add(table5);
            document.add(table6);
            document.add(table7);
            document.add(assinatura1);
            document.add(descricao1);
            document.add(assinatura2);
            document.add(descricao2);
            document.close();
            Toast.makeText(context, "PDF gerado com sucesso!", Toast.LENGTH_SHORT).show();
            MostraPDF(nome_completo, context);


        } catch (DocumentException e) {

            e.printStackTrace();

        } catch (FileNotFoundException e) {

            e.printStackTrace();

        }

    }

    private void MostraPDF(String arquivo, Context context) {

        File file = new File(arquivo);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {

            context.startActivity(intent);

        } catch (ActivityNotFoundException ex) {
            Toast.makeText(context, "Não há nenhum aplicativo que abra este tipo de dados!", Toast.LENGTH_SHORT).show();
        }

    }
}
