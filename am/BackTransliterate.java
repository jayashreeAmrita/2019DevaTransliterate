/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package am.transl;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 *
 * @author jayashreenair
 * 
 */
public class BackTransliterate 
{
    String cons="k,\\u0915\n" +
"kh,\\u0916\n" +
"g,\\u0917\n" +
"gh,\\u0918\n" +
"G,\\u0919\n" +
"c,\\u091A\n" +
"ch,\\u091B\n" +
"j,\\u091C\n" +
"jh,\\u091D\n" +
"J,\\u091E\n" +
"T,\\u091F\n" +
"Th,\\u0920\n" +
"D,\\u0921\n" +
"Dh,\\u0922\n" +
"N,\\u0923\n" +
"t,\\u0924\n" +
"th,\\u0925\n" +
"d,\\u0926\n" +
"dh,\\u0927\n" +
"n,\\u0928\n" +
"p,\\u092A\n" +
"ph,\\u092B\n" +
"b,\\u092C\n" +
"bh,\\u092D\n" +
"m,\\u092E\n" +
"y,\\u092F\n" +
"r,\\u0930\n" +
"l,\\u0932\n" +
"L,\\u0933\n" +
"v,\\u0935\n" +
"z,\\u0936\n" +
"S,\\u0937\n" +
"s,\\u0938\n" +
"h,\\u0939\n" +
"~,\\u094D";
    
    String vows="a,a\n" +
"A,\\u093E\n" +
"i,\\u093F\n" +
"I,\\u0940\n" +
"u,\\u0941\n" +
"U,\\u0942\n" +
"R,\\u0943\n" +
"lR,\\u0944\n" +
"e,\\u0947\n" +
"ai,\\u0948\n" +
"o,\\u094B\n" +
"au,\\u094C\n" +
"M,\\u0902\n" +
"H,\\u0903\n" +
":,\\u0903\n" +
"',\\u093D\n" +
"OM,\\u0950\n" +
"RR,\\u0944";
    String vows1="a,\\u0905\n" +
"A,\\u0906\n" +
"i,\\u0907\n" +
"I,\\u0908\n" +
"u,\\u0909\n" +
"U,\\u090A\n" +
"R,\\u090B\n" +
"lR,\\u090C\n" +
"e,\\u090F\n" +
"ai,\\u0910\n" +
"o,\\u0913\n" +
"au,\\u0914\n" +
"OM,\\u0950\n" +
"M,\\u0902\n" +
"H,\\u0903\n" +
":,\\u0903\n" +
"',\\u093D\n" +
"O,.\n" +
"RR,\\u0960\n" +
"1,\\u0967\n" +
"2,\\u0968\n" +
"3,\\u0969\n" +
"4,\\u096A\n" +
"5,\\u096B\n" +
"6,\\u096C\n" +
"7,\\u096D\n" +
"8,\\u096E\n" +
"9,\\u096F\n" +
"0,\\u0966";
    
    public static HashMap<String,String> getHMap(String str)
    {
        
        HashMap<String, String> resultMap=new HashMap<>();
        Scanner read=new Scanner(str);
        
        while(read.hasNextLine())
        {
            String s=read.nextLine();
            StringTokenizer st=new StringTokenizer(s,",");
            
            resultMap.put(st.nextToken(),st.nextToken());
            
        }
        
        return resultMap;
        
    }
    public static HashMap<String,String> getHMapfromCSV(String filePath)
    {
        Scanner read=null;
        HashMap<String, String> resultMap=new HashMap<>();
        try {
            read=new Scanner(new File(filePath));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(BackTransliterate.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(read.hasNextLine())
        {
            String s=read.nextLine();
            StringTokenizer st=new StringTokenizer(s,",");
            
            resultMap.put(st.nextToken(),st.nextToken());
            
        }
        return resultMap;
    }
    private HashMap<String,String> vow;
    private HashMap<String,String> vowBig;
    private HashMap<String,String> con;
    
    public BackTransliterate()
    {
        
            vow=getHMap(vows);
            con=getHMap(cons);
            vowBig=getHMap(vows1);
        
        
    }
    
    public String transliterate(String str)
    {
        
        int i=0;
        StringBuilder res=new StringBuilder();
       
        boolean C=false;
        boolean V=false;
        boolean T=false;
        
        String ss="";
        String temp="";
        boolean BA=false;
        int len=str.length();
        while(i<str.length())
        {
            temp="";ss="";
            if(con.containsKey(""+str.charAt(i))) //Check if the letter is consonant
            {
                C=true;
                V=false;
                
                
                int in=i;
                char c=str.charAt(in);
                
                if("kcgjTDtdpb".contains(""+str.charAt(i)))
                {
                    if(i!=str.length()-1)//not last
                       if(str.charAt(i+1)=='h') //check if next is h
                            T=true;
                }
                if(T) //Take TWO letters
                    {
                        temp=""+str.charAt(i++)+str.charAt(i++);
                        T=false;
                    }
                    else  temp=""+str.charAt(i++);
               
                ss=con.get(temp); //get unicode from HMap
            }
           else
                if(vowBig.containsKey(""+str.charAt(i))) //if its vowel
                {
                    
                    switch(str.charAt(i))
                    {
                        case 'a':
                            if(i!=str.length()-1)
                               if(str.charAt(i+1)=='u' || str.charAt(i+1)=='i' ) //check if next is h
                                T=true;
                            break;
                        
                        case 'O':
                             if(i<str.length()-1 && str.charAt(i+1)=='M')
                             
                                    T=true;
                               
                             else //unknown
                              {
                                    res.append(str.charAt(i++));
                                    V=C=false;
                                    continue;
                               }
                             break;
                        case 'M': case 'H':
                            if(!C && !V) //beginning of a word
                                res.append(""+'\u0905');
                            break;
                        case 'R':
                            if(i<str.length()-1)
                                if(str.charAt(i+1)=='R')
                                    T=true;
                        
                        
                    }
                    
                    if(T) 
                    {
                        temp+=""+str.charAt(i++)+str.charAt(i++);
                        T=false;
                    }
                    else  temp+=""+str.charAt(i++);
                    
                   V=true;
                  if(C) //previous was C
                     ss=vow.get(temp); 
                       
                  else
                  {
                      ss=vowBig.get(temp);
                      if(temp.equals("a"))
                          BA=true;
                  }
                    
                }
            else //unknown
                    {
                           
                          res.append(str.charAt(i++));
                          V=C=false;
                          continue;  
                    }
           if(V && C) //If its a Vowel and previous was Consonant
            {
                C=false;
                res.deleteCharAt(res.length()-1); //remove "/" from the consonant
                
                
                
            }
            if(!temp.equals("a")) // Append everything except "a" 
                {
                  char c = (char) Integer.parseInt(ss.substring(2), 16 );
                  res.append(c);
                  
                }
            
            
           if(C)
            {
                ss=con.get("~");
                char ct = (char) Integer.parseInt(ss.substring(2), 16 );
                res.append(ct);
                
                
            }
           if(BA)
           {
               res.append(""+'\u0905');
               BA=false;
           }
           
              
        }
        
        return(res.toString());
    }
     
}
