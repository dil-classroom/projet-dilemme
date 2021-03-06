package ch.heigvd.dil.utils.parsers;

import ch.heigvd.dil.data_structures.Page;
import java.text.ParseException;
import org.everit.json.schema.ValidationException;

/**
 * Permet de lire un fichier représentant une page et de parser la partie contenu et la partie
 * configuration.
 */
public class PageContentSeparator {
  private static final String SEP = "---";
  private final String content;
  private final Page.Config config;

  /**
   * Le constructeur sépare la partie contenu de la partie configuration et se charge de vérifier la
   * conformité de la configuration.
   *
   * @param file String contenant la page non parsée
   * @throws ParseException si la page ne contient pas de séparateur
   * @throws ValidationException si la configuration JSON n'est pas valide.
   */
  public PageContentSeparator(String file) throws ParseException, ValidationException {
    int index = file.indexOf(SEP);
    if (index == -1) {
      throw new ParseException("Invalid page format", 0);
    }
    config = genPageConfig(file.substring(0, index));
    content = file.substring(index + SEP.length());
  }

  /**
   * Récupère la partie contenu de la page
   *
   * @return contenu de la page
   */
  public String getContent() {
    return content;
  }

  /**
   * Récupère la partie configuration de la page.
   *
   * @return configuration valide
   */
  public Page.Config getConfig() {
    return config;
  }

  /**
   * Génère une configuration de page à partir d'un JSON.
   *
   * @param configStr JSON de la configuration
   * @return Une instance de Page.Config
   * @throws ValidationException si la configuration JSON n'est pas valide
   */
  private static Page.Config genPageConfig(String configStr) throws ValidationException {
    String schema = "schema/page-config-schema.json";
    ConfigGenerator<Page.Config> validator =
        new ConfigGenerator<>(configStr, schema, Page.Config.class);
    return validator.getConfigObject();
  }
}
