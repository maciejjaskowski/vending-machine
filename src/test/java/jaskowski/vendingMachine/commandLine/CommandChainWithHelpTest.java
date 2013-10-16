package jaskowski.vendingMachine.commandLine;

import org.junit.Test;

import java.io.PrintStream;
import java.util.List;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class CommandChainWithHelpTest {


    @Test
    public void shouldThrowExceptionOnUnknownCommand() throws Exception {
        //given
        CommandLink commandLink1 = mock(CommandLink.class);
        CommandLink commandLink2 = mock(CommandLink.class);
        CommandChainWithHelp commandChainWithHelp = aCommandChainWithHelp(asList(commandLink1, commandLink2));

        //when
        boolean exceptionCaught = false;
        try {
            commandChainWithHelp.interpret("unknown command");
        } catch (UnknownCommandException e) {
            exceptionCaught = true;
        }

        //then
        verify(commandLink1).tryInvoke("unknown command");
        verify(commandLink2).tryInvoke("unknown command");
        assertThat(exceptionCaught).isTrue();
    }

    @Test
    public void shouldTryInvokingToFirstSuccessOnly() throws Exception {
        //given
        CommandLink commandLink1 = commandRespondingTo("commandLink1");
        CommandLink commandLink2 = commandRespondingTo("something else");
        CommandChainWithHelp commandChainWithHelp = aCommandChainWithHelp(asList(commandLink1, commandLink2));

        //when
        commandChainWithHelp.interpret("commandLink1");

        //then
        verify(commandLink1).tryInvoke("commandLink1");
        verify(commandLink2, never()).tryInvoke(anyString());
    }

    @Test
    public void shouldTryInvokingAllIfNecessary() throws Exception {
        //given
        CommandLink commandLink1 = commandRespondingTo("something");
        CommandLink commandLink2 = commandRespondingTo("something else");
        CommandLink commandLink3 = commandRespondingTo("yet something else");
        CommandChainWithHelp commandChainWithHelp = aCommandChainWithHelp(asList(commandLink1, commandLink2, commandLink3));

        //when
        commandChainWithHelp.interpret("yet something else");

        //then
        verify(commandLink1).tryInvoke("yet something else");
        verify(commandLink2).tryInvoke("yet something else");
        verify(commandLink3).tryInvoke("yet something else");
    }

    @Test
    public void shouldRespondToHelpCommand() throws Exception {
        //given
        CommandLink commandLink1 = commandWithHelpText("help text");
        PrintStream printStream = mock(PrintStream.class);
        CommandChainWithHelp commandChainWithHelp = new CommandChainWithHelp(asList(commandLink1), printStream);

        //when
        commandChainWithHelp.interpret("help");

        //then
        verify(printStream).println("help text");
    }

    private CommandChainWithHelp aCommandChainWithHelp(List<CommandLink> commandLinks) {
        return new CommandChainWithHelp(commandLinks, System.out);
    }

    private CommandLink commandWithHelpText(String helpText) {
        CommandLink commandLink1 = mock(CommandLink.class);
        when(commandLink1.explain()).thenReturn(helpText);
        return commandLink1;
    }

    private CommandLink commandRespondingTo(String text) {
        CommandLink commandLink1 = mock(CommandLink.class);
        when(commandLink1.tryInvoke(text)).thenReturn(true);
        return commandLink1;
    }


}
