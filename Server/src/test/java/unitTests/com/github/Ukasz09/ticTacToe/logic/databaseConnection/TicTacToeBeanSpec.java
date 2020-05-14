package unitTests.com.github.Ukasz09.ticTacToe.logic.databaseConnection;

import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeBean;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class TicTacToeBeanSpec {
    private TicTacToeBean bean;
    private final int turn = 17;
    private final int x = 2;
    private final int y = 3;
    private final char player = 'X';

    @BeforeEach
    public void initBean() {
        bean = new TicTacToeBean(turn, x, y, player);
    }

    @Test
    public void whenInstantiatedThenIdIsStored() {
        assertEquals(turn, bean.getTurn());
    }

    @Test
    public void whenInstantiatedThenXIsStored() {
        assertEquals(x, bean.getCordX());
    }

    @Test
    public void whenInstantiatedThenYIsStored() {
        assertEquals(y, bean.getCordY());
    }

    @Test
    public void whenInstantiatedThenPlayerIsStored() {
        assertEquals(player, bean.getPlayer());
    }
}
