/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;



import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfWriter;

import com.lowagie.text.pdf.draw.LineSeparator;
import java.awt.Color;


import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author mauro di
 */
public class PdfConsolidado extends Thread{
    private ArrayList lstPedidos;
    private Connection cn;
    private Integer idListado;
    private Integer idRevision;
    private String rutaDestino;
    private String rutaDestinoRemoto;
    private String vehiculo;
    private String totalKg;
    private String fechaReparto;
    private String pie;
    private String codigoArticulo;
    private String descripcion;
    private String cantidad;
    private String pesoIndividual;
    private String totalKgListado;
    private String codigoCliente;
    private String razonSocial;

    public ArrayList getLstPedidos() {
        return lstPedidos;
    }

    public void setLstPedidos(ArrayList lstPedidos) {
        this.lstPedidos = lstPedidos;
    }

    public Integer getIdListado() {
        return idListado;
    }

    public void setIdListado(Integer idListado) {
        this.idListado = idListado;
    }

    public Integer getIdRevision() {
        return idRevision;
    }

    public void setIdRevision(Integer idRevision) {
        this.idRevision = idRevision;
    }

    public String getRutaDestino() {
        return rutaDestino;
    }

    public void setRutaDestino(String rutaDestino) {
        this.rutaDestino = rutaDestino;
    }

    public String getRutaDestinoRemoto() {
        return rutaDestinoRemoto;
    }

    public void setRutaDestinoRemoto(String rutaDestinoRemoto) {
        this.rutaDestinoRemoto = rutaDestinoRemoto;
    }

    public String getVehiculo() {
        return vehiculo;
    }

    public void setVehiculo(String vehiculo) {
        this.vehiculo = vehiculo;
    }

    public String getTotalKg() {
        return totalKg;
    }

    public void setTotalKg(String totalKg) {
        this.totalKg = totalKg;
    }

    public String getFechaReparto() {
        return fechaReparto;
    }

    public void setFechaReparto(String fechaReparto) {
        this.fechaReparto = fechaReparto;
    }

    public String getPie() {
        return pie;
    }

    public void setPie(String pie) {
        this.pie = pie;
    }

    public String getCodigoArticulo() {
        return codigoArticulo;
    }

    public void setCodigoArticulo(String codigoArticulo) {
        this.codigoArticulo = codigoArticulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPesoIndividual() {
        return pesoIndividual;
    }

    public void setPesoIndividual(String pesoIndividual) {
        this.pesoIndividual = pesoIndividual;
    }

    public String getTotalKgListado() {
        return totalKgListado;
    }

    public void setTotalKgListado(String totalKgListado) {
        this.totalKgListado = totalKgListado;
    }

    public String getCodigoCliente() {
        return codigoCliente;
    }

    public void setCodigoCliente(String codigoCliente) {
        this.codigoCliente = codigoCliente;
    }

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    
    
    

    public Connection getCn() {
        return cn;
    }

    public void setCn(Connection cn) {
        this.cn = cn;
    }
    

    public PdfConsolidado(Connection cn, Integer idListado, Integer idRevision, String vehiculo, String kilos, String fechaEntrega, String rutaDestino, String rutaDestinoRemoto) {
        this.cn = cn;
        this.idListado = idListado;
        this.vehiculo=vehiculo;
        this.fechaReparto=fechaEntrega;
        this.totalKgListado=kilos;
        
        this.idRevision = idRevision;
        this.rutaDestino = rutaDestino;
        this.rutaDestinoRemoto = rutaDestinoRemoto;
        //this.lstPedidos=lstPedidos;
    }

    public PdfConsolidado() {
    }

    

    
    
    

    
    
    @Override
    public void run(){
        Document documento=new Document();
        int i=1;
        //String clienteF=doc.getAfipPlastCbte().replace(":","_");
        String arch=this.rutaDestino;
        
        Date date = new Date();
        DateFormat hourdateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        File fich=new File(arch);
        while(fich.exists()){
            i++;
            int rev=this.idRevision++;
            arch="c:\\listadosHdr\\"+this.idListado+"-Rev "+rev+" - listado consolidado de materiales.pdf";
            fich=new File(arch);
        }
        FileOutputStream fichero;
        try {
            
            
            fichero=new FileOutputStream(arch);
            PdfWriter writer=PdfWriter.getInstance(documento, fichero);
            documento.open();
            PdfContentByte cb=writer.getDirectContent();
            /*
            if(Propiedades.getLOGO() != ""){
                Image imagen= Image.getInstance(Propiedades.getLOGO());
                imagen.scaleAbsolute(190, 110);
                documento.add(imagen);
            }
            */
            //String sql1=null;
            String sql="select pedidos_carga1.NRO_PEDIDO,pedidos_carga1.COD_CLIENT,pedidos_carga1.listado,pedidos_carga1.RAZON_SOC,pedidos_carga1.COD_ARTIC,pedidos_carga1.DESC_ARTIC,sum(pedidos_carga1.CANT_PEDID * pedidos_carga1.peso)as total,(select sum(movimientoslpm.cantidadNueva) from movimientoslpm where movimientoslpm.numeroListado="+this.getIdListado()+" and codigoArticulo=pedidos_carga1.COD_ARTIC) as cantidadNueva,(select sum(movimientoslpm.cantidadNueva) from movimientoslpm where movimientoslpm.numeroListado="+this.getIdListado()+"  and revision < "+this.getIdRevision()+" and codigoArticulo=pedidos_carga1.COD_ARTIC) as cantidadVieja,left(pedidos_carga1.entrega,10)as entrega,pedidos_carga1.vehiculo,(select unidades.descripcion from unidades where unidades.numero=pedidos_carga1.vehiculo)as descripcion,sum(pedidos_carga1.CANT_PEDID * pedidos_carga1.peso)as totalList from pedidos_carga1 where listado="+this.getIdListado()+" and COD_ARTIC not like '999%' and COD_ARTIC not like '30030011%' and COD_ARTIC not like '90020010%' and orden_num=0 and chk=0 group by COD_ARTIC";
            System.out.println(sql);
            Statement st=this.getCn().createStatement();
            //Statement st1=this.getCn().createStatement();
            //ResultSet rs1 = null;
            ResultSet rs=st.executeQuery(sql);
            PdfConsolidado pdfC;
            this.lstPedidos=new ArrayList();
            String unidad = null;
            String kgListado = null;
            String fechaR = null;
            
            while(rs.next()){
                pdfC=new PdfConsolidado();
                pdfC.setCodigoCliente(rs.getString("COD_CLIENT"));
                pdfC.setIdListado(rs.getInt("listado"));
                pdfC.setRazonSocial(rs.getString("RAZON_SOC"));
                pdfC.setVehiculo(rs.getString("descripcion"));
                pdfC.setCodigoArticulo(rs.getString("COD_ARTIC"));
                pdfC.setDescripcion(rs.getString("DESC_ARTIC"));
                pdfC.setPesoIndividual(rs.getString("total"));
                unidad=pdfC.getVehiculo();
                fechaR=rs.getString("entrega");
                kgListado=rs.getString("totalList");
                this.lstPedidos.add(pdfC);
                        
            }
            
            ArrayList lstClientes=new ArrayList();
            sql="select COD_CLIENT,RAZON_SOC from pedidos_carga1 where listado="+this.getIdListado()+" and chk=0 group by COD_CLIENT,RAZON_SOC";
            rs=st.executeQuery(sql);
            PdfConsolidado ccp;
            while(rs.next()){
                ccp=new PdfConsolidado();
                ccp.setCodigoCliente(rs.getString("COD_CLIENT"));
                ccp.setRazonSocial(rs.getString("RAZON_SOC"));
                lstClientes.add(ccp);
            }
            rs.close();
            LineSeparator linea=new LineSeparator();
            Rectangle recta = null;
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
            cb.setFontAndSize(bf,8);
            cb.beginText();
            cb.setTextMatrix(40,820);
            cb.showText("Listado de Preparación de Cargar Nº: "+this.getIdListado());
            //cb.showText("eR&Re");
            //cb.add(imagen);
            cb.setFontAndSize(bf,8);
            cb.setTextMatrix(40, 810);
            cb.showText("Total Kg.: "+this.getTotalKgListado());
            cb.setTextMatrix(370,810);
            cb.showText("Vehiculo: "+unidad);
            cb.setTextMatrix(370,800);
            cb.showText("Fecha de entrega: "+fechaR);
            //cb.showText("PAPELES");
            //bf = BaseFont.createFont(BaseFont.COURIER,BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
            //cb.setFontAndSize(bf,8);
            
            cb.setTextMatrix(40,800);
            cb.showText("Fecha y Hora de Impresión: "+hourdateFormat.format(date));
            /*
             bf = BaseFont.createFont(BaseFont.COURIER,BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
            cb.setFontAndSize(bf,8);
            cb.setTextMatrix(40,790);
            cb.showText("Se corresponde con Rev: "+this.getIdRevision());
            */
            
            //cb.showText("de Rivadeneira Enrique y Rivadeneira Jorge S.H.");
            bf = BaseFont.createFont(BaseFont.COURIER_BOLD,BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
            cb.setFontAndSize(bf,10);
            //cb.setLineWidth(1f);
            //cb.roundRectangle(370,820,40,150, 10);
            linea.setLineWidth(2);
            linea.drawLine(cb,40,550,790);
            
            cb.setTextMatrix(230,778);
            cb.showText("CONSOLIDADO / ULT REV Nº "+this.getIdRevision());
            
            int renglon=0;
            linea.setLineWidth(1);
            linea.drawLine(cb,40,550,770);
            cb.setFontAndSize(bf,8);
            cb.setTextMatrix(40,760);
            cb.showText("Articulo");
            cb.setTextMatrix(140,760);
            cb.showText("Descripcion");
            cb.setTextMatrix(350,760);
            cb.showText("Cantidad");
            cb.setTextMatrix(450,760);
            cb.showText("Peso / KG.");
            
            linea.setLineWidth(1);
            linea.setLineColor(Color.GRAY);
            linea.drawLine(cb, 40, 550, 750);
            
            renglon=745;
            
            
            //ACA COMIENZA EL BUCLE DE ENCABEZADO DE PEDIDOS
            PdfConsolidado pdf;
            Iterator it=this.getLstPedidos().listIterator();
            int contador=0;
            while(it.hasNext()){
                pdf=(PdfConsolidado) it.next();
            
                //INICIO RENGLON
                bf = BaseFont.createFont(BaseFont.COURIER,BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
                cb.setFontAndSize(bf,6);
                cb.setTextMatrix(40,renglon);
                cb.showText(pdf.getCodigoArticulo());
                cb.setTextMatrix(140,renglon);
                cb.showText(pdf.getDescripcion());
                cb.setTextMatrix(350,renglon);
                
                cb.showText(pdf.getCantidad());
                bf = BaseFont.createFont(BaseFont.COURIER,BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
                cb.setFontAndSize(bf,6);
                cb.setTextMatrix(450,renglon);
                cb.showText(pdf.getPesoIndividual());
                //VERIFICACION DE PIE
                
                if(renglon < 95){
                    linea.setLineWidth(1);
                    linea.setLineColor(Color.GRAY);
                    linea.drawLine(cb, 40, 550, renglon);
                    renglon=renglon - 10;
                    cb.setTextMatrix(40, renglon);
                    cb.showText("Cochabamba 4252 - 3000 Santa Fe - Tel/Fax rotativo (0342) 453-3362 \n Líneas Rotativas: (0342) 453-2022 / 400-8686 Linea Gratuita Local: 0800-222-9646 \n sidercon@sidercon.com - www.sidercon.com");
                    
                    cb.endText();
                    documento.newPage();

                    cb.beginText();

                    bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
                    cb.setFontAndSize(bf,8);
                    cb.setTextMatrix(40,760);
                    cb.showText("Articulo");
                    cb.setTextMatrix(140,760);
                    cb.showText("Descripcion");
                    cb.setTextMatrix(350,760);
                    cb.showText("Cantidad");
                    cb.setTextMatrix(450,760);
                    cb.showText("Peso / KG.");

                    linea.setLineWidth(1);
                    linea.setLineColor(Color.GRAY);
                    linea.drawLine(cb, 40, 550, 750);
                    
                    renglon=745;
                        
                        
                    }
                
                //FINAL VERIFICACION
                
                
                renglon=renglon - 10;
                //FINAL RENGLON
                
                //INICIO DETALLE PEDIDO
                //ENCABEZADO
                
                
               
                

            }
            //rs1.close();
            //FIN PEDIDO
            
            
            
            //pie de documento
            //renglon=50;
            renglon=renglon - 20;
            cb.setTextMatrix(350,renglon);
            cb.showText("________________________________");
            renglon=renglon - 10;
            cb.setTextMatrix(350,renglon);
            cb.showText("Total Kg. Rev.: "+kgListado);
            renglon=renglon - 10;
            cb.setTextMatrix(40,renglon);
            bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
            cb.setFontAndSize(bf,8);
            cb.showText("LISTADO DE CLIENTES");
            renglon=renglon - 5;
            cb.setTextMatrix(40,renglon);
            cb.showText("___________________");
            renglon=renglon - 10;
            Iterator itC=lstClientes.listIterator();
            while(itC.hasNext()){
                pdf=(PdfConsolidado) itC.next();
                bf = BaseFont.createFont(BaseFont.COURIER,BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
                cb.setFontAndSize(bf,6);
                cb.setTextMatrix(40,renglon);
                cb.showText(pdf.getCodigoCliente());
                cb.setTextMatrix(140,renglon);
                cb.showText(pdf.getRazonSocial());
                if(renglon < 95){
                    linea.setLineWidth(1);
                    linea.setLineColor(Color.GRAY);
                    linea.drawLine(cb, 40, 550, renglon);
                    renglon=renglon - 10;
                    cb.setTextMatrix(40, renglon);
                    cb.showText("Cochabamba 4252 - 3000 Santa Fe - Tel/Fax rotativo (0342) 453-3362 \n Líneas Rotativas: (0342) 453-2022 / 400-8686 Linea Gratuita Local: 0800-222-9646 \n sidercon@sidercon.com - www.sidercon.com");
                    
                    cb.endText();
                    documento.newPage();

                    cb.beginText();
                    
                    bf = BaseFont.createFont(BaseFont.HELVETICA_BOLD,BaseFont.CP1252,BaseFont.NOT_EMBEDDED);
                    cb.setFontAndSize(bf,8);
                    cb.showText("LISTADO DE CLIENTES");
                    renglon=renglon - 5;
                    cb.setTextMatrix(40,renglon);
                    cb.showText("___________________");
                    renglon=790;
                }
                renglon=renglon - 10;
                
            }
            
            
            
            
            cb.endText();
            documento.close();
            
            File f=new File(arch);
            if(f.exists()){
            
                Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler "+arch);
                File remoto=new File(this.getRutaDestinoRemoto());
                InputStream in=new FileInputStream(arch);
                OutputStream out=new FileOutputStream(remoto);
                byte[] buf = new byte[1024];
                int len;

                while ((len = in.read(buf)) > 0) {
                  out.write(buf, 0, len);
                }
                in.close();
                out.close();
            }
            int confirmacion=0;
            
            
            /*
            if(doc.getArchivo().isEmpty()){
                
            }else{
                confirmacion=JOptionPane.showConfirmDialog(null, "DESEA NOTIFICAR POR MAIL?");
            if(confirmacion==0){
                //JOptionPane.showMessageDialog(null,"acepto");
                
            }
            }
                    */
            System.out.println("eligio "+confirmacion);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PdfConsolidado.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
            
        } catch (DocumentException ex) {
            Logger.getLogger(PdfConsolidado.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        } catch (IOException ex) {
            Logger.getLogger(PdfConsolidado.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        } catch (SQLException ex) {
            Logger.getLogger(PdfConsolidado.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex);
        }
        
        
    }
    
}
