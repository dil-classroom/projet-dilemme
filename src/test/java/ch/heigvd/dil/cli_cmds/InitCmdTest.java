package ch.heigvd.dil.cli_cmds;

import static org.junit.Assert.*;

import ch.heigvd.dil.Main;
import ch.heigvd.dil.utils.FileHandler;
import java.nio.file.Path;
import org.junit.Test;
import picocli.CommandLine;

public class InitCmdTest {

  @Test
  public void initializeSiteWithValidPathShouldSucceed() {
    Path site = TestUtils.generateRandomSitePath();
    int exitCode = new CommandLine(new Main()).execute("init", site.toString());
    assertEquals(exitCode, 0);

    FileHandler.delete(site.toFile());
  }

  @Test
  public void initializeSiteWithInvalidPathShouldFail() {
    int exitCode = new CommandLine(new Main()).execute("init", "/invalid/path");
    assertNotEquals(exitCode, 0);
  }

  @Test
  public void initialiseSiteWithoutPathShouldFail() {
    int exitCode = new CommandLine(new Main()).execute("init");
    assertNotEquals(exitCode, 0);
  }
}
