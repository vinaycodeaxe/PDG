

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ILLUMINATI
 */
public class Pdg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ArrayList<d> def=new ArrayList<d>();
        ArrayList<u>     use=new ArrayList<u>();
        
        int count=-1;
        String line;
         FileInputStream fstream12;
        try {
            fstream12 = new FileInputStream("in.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream12));
            try {
                int ln=-1;
                while ((line = br.readLine()) != null)   {
                    line=line.trim();
                    ln++;
                    if (line.length()!=0) {
                        if (line.contains(";") || line.contains("}")  || line.contains("{")) {
                         count++;
                            System.out.println(count+"  "+line);
                           
                        }
                    }
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(A1.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(A1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
//        System.out.println("CPint  "+count);
        
        System.out.println("\nCONTROL DEPENDENCY GRAPH:\n");
        String strline;
        int[] a=new int[100];
        int[][] cd=new int[count][count];
        int index=0;
        FileInputStream fstream;
        try {
            fstream = new FileInputStream("in.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            try {
                int ln=-1;
                while ((strline = br.readLine()) != null)   {
                    strline=strline.trim();
                    if (strline.length()!=0) {
                       if (strline.contains(";") || strline.contains("}") || strline.contains("{") ) {
                           ln++;
                           if (strline.contains("{")) {
                               if (strline.contains("else")) {
                                   index++;
                               }else{
                                   if (index != 0) {
                                       cd[a[index - 1]][ln] = 1;
                                   }
                                   a[index++] = ln;

                                   if (strline.contains("while")) {
                                       cd[ln][ln] = 1;
                                   }
                               
                               }
                               
                              
                           }else if(strline.contains("}")){
                               index--;
                           }else{
                               cd[a[index-1]][ln]=1;
                           }
                       
                       }
                    }
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(A1.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(A1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
//        boolean check =true;
//        for (int i = 0; i < count; i++) {
//             if (check) {
//                    for (int k = 0; k <count; k++) {
//                        System.out.print("\t"+k+"");
//                        
//                    }
//                    check=false;
//                    System.out.println("");
//                }
//              System.out.print(""+(i)+"\t");
//            for (int j = 0; j < count; j++) {
//               
//                if (cd[i][j]==0) {
//                    System.out.print(cd[i][j]+"\t");
//                }else{
//                //System.out.print((char)27+"[34;43m"+cd[i][j]+ (char)27 + "[0m");
//                  //  System.out.print("\t");
//                    System.out.print(cd[i][j]+"\t");
//                }
//            }
//            System.out.println("");
//        }
        
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                System.out.print(""+cd[i][j]);
            }
            System.out.println("");
        }
        
        
        
        //DATA DEPENDENCY
        //System.out.println((char)27 + "[34;43mBlue text with yellow background");
        
        System.out.println("\nDATA DEPENDENCY\n");
        
        int[][] dd=new int[count][count];
        
         
         
        try {
            fstream12 = new FileInputStream("in.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream12));
            try {
                int ln=-1;
                while ((line = br.readLine()) != null)   {
                  
                 line=line.trim();
                    
                    
                    if (line.length()!=0) {
                        if (line.contains(";") || line.contains("}")  || line.contains("{")) {
                              ln++;
                                use.add(new u(ln));
                                def.add(new d(ln,""));
                            
                            if (line.contains("=")  && (!line.contains("<")) &&(!line.contains(">") &&(!line.contains("=="))) ) {
                                String[] part=line.split("=");
                                String[] p=part[0].split(" ");
                                String defined="";
                                if (p.length==1) {
                                    defined=p[0].trim();
                                }else{
                                    defined=p[1].trim();
                                }
                                //def.add(new d(ln,defined));
                                def.get(def.size()-1).s=defined;
                                String p1[]=part[1].split("[+\\-*;\\/]+");
                                for (int i = 0; i < p1.length; i++) {
                                    use.get(use.size()-1).uvar.add(p1[i].trim());
                                }
                                
                                
                            }else if((line.contains("<"))  || (line.contains(">"))  || (line.contains("=="))){
                                String p2[]=line.split("[<>\\(<=\\)\\(>=\\)\\(==\\)]+");
                                for (int i = 0; i < p2.length; i++) {
                                        p2[i]=p2[i].trim();
                                    if ((!p2[i].equalsIgnoreCase("{")) &&(!p2[i].equalsIgnoreCase("while")) &&(!p2[i].equalsIgnoreCase("if")) ) {
                                        use.get(use.size()-1).uvar.add(p2[i]);
                                    }
                                    
                                }
                            }else if(line.contains("printf(")){
                                
                                for (int i = 0; i < line.length(); i++) {
                                    String temp="";
                                    if (line.charAt(i)==',') {
                                        for (int j = i+1; j < line.length(); j++) {
                                            if (line.charAt(j)==',' || line.charAt(j)==')') {
                                                use.get(use.size()-1).uvar.add(temp.trim());
                                                //System.out.println(""+temp);
                                                
                                                break;
                                            }else{
                                                temp=temp+line.charAt(j);
                                            }
                                        }
                                    }
                                }
                            
                            }
                           
                        }
                    }
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(A1.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(A1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
         boolean  wh=false;
         int num=0;
         int tc=0;
        try {
            fstream12 = new FileInputStream("in.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream12));
            try {
                int ln=-1;
                while ((line = br.readLine()) != null)   {
                    line=line.trim();
                    if (line.length()!=0) {
                        if (line.contains(";") || line.contains("}")  || line.contains("{")) {
                            ln++;
                            if (line.contains("while")) {
                                wh=true;
                                num=ln;
                                tc++;
                            } if (line.contains("{") && wh) {
                                tc++;
                            }if (line.contains("}") && wh) {
                                tc--;
                            }
                            if (tc==0) {
                                wh=false;
                            }
                              if (line.contains("=")  && (!line.contains("<")) &&(!line.contains(">") &&(!line.contains("=="))) )  {
                                
                                 if (wh) {
                                     String s2=def.get(ln).s;
                                     if (!s2.equalsIgnoreCase("")) {
                                     for (int i = num; i < use.size(); i++) {
                                         if (!use.get(i).uvar.isEmpty()) {
                                                 for (int j = 0; j < use.get(i).uvar.size(); j++) {
                                                     if (s2.equalsIgnoreCase(use.get(i).uvar.get(j))) {
                                                        // System.out.println(""+ln);
                                                         //System.out.println(""+use.get(i).line);
                                                        
                                                         dd[ln][use.get(i).line]=1;
                                                     }
                                                 }
                                             }
                                     }
                                     }
                                
                            }else{
                                String s1=def.get(ln).s;
                                     if (!s1.equalsIgnoreCase("")) {
                                         for (int i = ln+1; i <use.size(); i++) {
                                             if (!use.get(i).uvar.isEmpty()) {
                                                 for (int j = 0; j < use.get(i).uvar.size(); j++) {
                                                     if (s1.equalsIgnoreCase(use.get(i).uvar.get(j))) {
                                                        // System.out.println(""+ln);
                                                         //System.out.println(""+use.get(i).line);
                                                        
                                                         dd[ln][use.get(i).line]=1;
                                                     }
                                                 }
                                             }
                                         }
                                     }
                            
                            }
                            
                            }
                           
                            
                           
                        }
                    }
                    
                }
            } catch (IOException ex) {
                Logger.getLogger(A1.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(A1.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        
//        check =true;
//        for (int i = 0; i < count; i++) {
//             if (check) {
//                    for (int k = 0; k <count; k++) {
//                        System.out.print("\t"+k+"");
//                        
//                    }
//                    check=false;
//                    System.out.println("");
//                }
//              System.out.print(""+(i)+"\t");
//            for (int j = 0; j < count; j++) {
//               
//                if (cd[i][j]==0) {
//                    System.out.print(dd[i][j]+"\t");
//                }else{
//                //System.out.print((char)27+"[34;43m"+cd[i][j]+ (char)27 + "[0m");
//                  //  System.out.print("\t");
//                    System.out.print(dd[i][j]+"\t");
//                }
//            }
//            System.out.println("");
//        }
        
        
         for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                System.out.print(""+dd[i][j]);
            }
             System.out.println("");
        }
        
        
        
        
        System.out.println("\nPROGRAM DEPENDENCY GRAPH\n");
        
        for (int i = 0; i < count; i++) {
            for (int j = 0; j < count; j++) {
                if ((cd[i][j]==1) || (dd[i][j]==1)) {
                    System.out.print("1");
                }else{
                    System.out.print("0");
                }
                    
            }
            System.out.println("");
        }
        
    
        
    }
    
}


class u{
    int line;
    ArrayList<String> uvar=new ArrayList<String>();
    
    u(int line){
this.line=line;
}
}




class d{
int line;
String s;
d(int line,String s){
    this.line=line;
    this.s=s;
}
}
