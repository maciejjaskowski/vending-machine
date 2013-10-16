package jaskowski.vendingMachine.commandLine;


import org.junit.Test;

import static org.mockito.Mockito.*;

public class SafeCommandChainTest {


   @Test
   public void shouldDelegateToDelegatee() throws Exception {
       //given
       CommandChain commandChain = mock(CommandChain.class);
       SafeCommandChain safeCommandChain = new SafeCommandChain(commandChain);

       //when
       safeCommandChain.interpret("known command");

       //then
       verify(commandChain).interpret("known command");
   }

   @Test
   public void shouldSilentUnknownCommandException() throws Exception {
       //given
       CommandChain commandChain = mock(CommandChain.class);
       SafeCommandChain safeCommandChain = new SafeCommandChain(commandChain);
       doThrow(new UnknownCommandException()).when(commandChain).interpret("unknown command");

       //when
       safeCommandChain.interpret("unknown command");

       //then
       verify(commandChain).interpret("unknown command");
   }
}
