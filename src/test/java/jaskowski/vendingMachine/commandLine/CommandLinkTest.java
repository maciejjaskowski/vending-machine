package jaskowski.vendingMachine.commandLine;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CommandLinkTest {

    @Mock
    private Command command;


    @Before
    public void setUp() {

    }

    @Test
    public void shouldNotInvokeCommandIfPatternDoesNotMatch() {
        //given
        CommandLink commandLink = new CommandLink(Pattern.compile("simple pattern"), command);

        //when
        boolean invoked = commandLink.tryInvoke("not matching");

        //then
        assertThat(invoked).isFalse();
        verify(command, never()).command(any(Object.class));
    }

    @Test
    public void shouldInvokeCommandIfPatternMatches() {
        //given
        CommandLink commandLink = new CommandLink(Pattern.compile("simple pattern"), command);

        //when
        boolean invoked = commandLink.tryInvoke("simple pattern");

        //then
        assertThat(invoked).isTrue();
        verify(command).command(null);
    }

    @Test
    public void shouldInvokeCommandWithParameterIfPatternMatches() {
        //given
        Command<String> command = mock(Command.class);
        CommandLink commandLink = new CommandLink(Pattern.compile("with param (\\d+)"), command);

        //when
        boolean invoked = commandLink.tryInvoke("with param 5");

        //then
        assertThat(invoked).isTrue();
        verify(command).command("5");
    }
}
