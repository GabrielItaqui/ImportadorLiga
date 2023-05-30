package br.com.mercadoturbo.liga.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScraperLigaService {

    public static void main(String[] args) {
        String url = "https://www.omoshiroi.com.br/?view=ecom/itens&tcg=1&txt_estoque=1&itens_total=1917&txt_limit=120&page=1";

        try {
            Document doc = Jsoup.connect(url).get();
            Elements cardElements = doc.select(".card-item");

            for (Element cardElement : cardElements) {
                // Nome do Card
                Element titleElement = cardElement.selectFirst(".title");
                String cardName = titleElement.text();

                // Quantidade de Estoque
                Element qtyElement = cardElement.selectFirst(".qty > span");
                Integer qty = Integer.valueOf(qtyElement.text());

                // Preço do Card
                Element priceElement = cardElement.selectFirst(".price");
                String price = priceElement.text();

                // URL da Imagem
                String imageUrl = null;

                Elements imgElements = cardElement.select(".card-img img");
                if (!imgElements.isEmpty()) {
                    Element imgElement = imgElements.first();
                    imageUrl = imgElement.attr("src");
                } else {
                    // Caso a URL da imagem não seja encontrada, tenta buscar por regex ou outro método
                    // Aqui, estamos usando um padrão de regex para buscar a URL da imagem no HTML
                    Pattern pattern = Pattern.compile("<img src=\"(.*?)\"");
                    Matcher matcher = pattern.matcher(cardElement.html());
                    if (matcher.find()) {
                        imageUrl = matcher.group(1);
                    }
                }

                System.out.println("Nome do Card: " + cardName);
                System.out.println("Quantidade de Estoque: " + qty);
                System.out.println("Preço do Card: " + price);
                System.out.println("URL da Imagem: " + imageUrl);
                System.out.println();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}