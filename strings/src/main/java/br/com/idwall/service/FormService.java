package br.com.idwall.service;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import br.com.idwall.model.Form;

@Component
public class FormService {

	public void formatString(Form form) {

		List<String> result = new ArrayList<>();
		
		if (!form.isJustify()) {

			Pattern p = Pattern.compile("\\G\\s*(.{1," + form.getLength() + "})(?=\\s|$)", Pattern.DOTALL);
			Matcher m = p.matcher(form.getContent());

			while (m.find()) {
				result.add(m.group());
			}

		} else {

			result = fullJustify(form.getContent().split(" "), form.getLength());

		}

		form.setFormattedContent(result);
		System.out.println(result);

	}
	
    public ArrayList<String> fullJustify(String[] words, int L) {
    	
        ArrayList<String> resultList = new ArrayList<String>();
        if(words == null || words.length == 0) return resultList;
        int i = 0;
        
        while(i < words.length){
        	
            int sumLen = words[i].length();
            int j = i+1;
            while(j < words.length && sumLen + 1 + words[j].length() <= L){
                sumLen = sumLen + 1 + words[j].length();
                j += 1;
            }
            StringBuilder sb = new StringBuilder();
            if(j == words.length){
                for(int k = i; k < j; k++){
                    sb.append(words[k]);
                    if(k == j-1) break;
                    sb.append(" ");
                }
                resultList.add(sb.toString());
                break;
            }
            int diff = L-sumLen;
            int gapNum = j-i-1;
            if(gapNum == 0){
                resultList.add(words[i]);
                i = j;
                continue;   
            }
            int gapLen0 = diff/gapNum+1;
            int gapLen1Num = diff%gapNum;
            String s0 = genStr(gapLen0);
            int count = 0;
            for(int k = i; k < j; k++){
                sb.append(words[k]);
                if(k == j-1) break;
                sb.append(s0);
                if(count < gapLen1Num){
                    sb.append(" ");
                    count ++;
                }
            }
            resultList.add(sb.toString());
            i = j;
        }
        
        
        for(int j = 0; j < resultList.size(); j++){
            String s = resultList.get(j);
            if(s.length() != L){
                int diff = L - s.length();
                resultList.set(j, s + genStr(diff));
            }
        }
        
        return resultList;
    }

    public String genStr(int num){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < num; i++){
            sb.append(" ");
        }
        return sb.toString();
    }	

}