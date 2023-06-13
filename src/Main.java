import java.util.BitSet;

public class Main {
    private static final int horizontalMarker = Integer.MIN_VALUE;
    private static final int verticalMarker = Integer.MAX_VALUE;
    public static void main(String[] args) {
        int [][] matrix = {
                {75, 24, 36, 48, 57},
                {67, 0, 86, 98, 10},
                {11, 12, 13, 14, 15},
                {16, 17, 18, 0, 20}
        };
        int [][] matrixCopy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; ++i) {
            matrixCopy[i] = new int[matrix[i].length];
            System.arraycopy(matrix[i], 0, matrixCopy[i], 0, matrixCopy[i].length);
        }
        matrix = zeroMatrixWithBitSetMatrix(matrix);
        printMatrix(matrix);
        System.out.println();
        matrixCopy = zeroMatrixInPlace(matrixCopy);
        printMatrix(matrixCopy);
    }
    public static int[][] zeroMatrixWithBitSetMatrix(int[][] matrix) {
        BitSet[] bitSets = markZeroesWithBitSet(matrix);
        for(int i = 0; i < matrix.length; i++) {
            if(!bitSets[i].isEmpty()) {
                matrix = setColumnToZero(matrix, i);
                int rowWithZero = bitSets[i].nextSetBit(0);
                while(rowWithZero > 0) {
                    setRowToZero(matrix, rowWithZero);
                    rowWithZero = bitSets[i].nextSetBit(++rowWithZero);
                }
            }
        }
        return matrix;
    }
    private static BitSet[] markZeroesWithBitSet(int[][] matrix) {
        BitSet[] bitSets = new BitSet[matrix.length];
        for(int i = 0; i < bitSets.length; i++) {
            bitSets[i] = new BitSet();
        }
        for(int row = 0; row < matrix.length; row++) {
            for(int column = 0; column < matrix[0].length; column++) {
                if(matrix[row][column] == 0) {
                    bitSets[row].set(column);
                }
            }
        }
        return bitSets;
    }
    private static int[][] setRowToZero(int [][] matrix, int row) {
        for(int i = 0; i < matrix[0].length; i++) {
            matrix[row][i] = 0;
        }
        return matrix;
    }
    private static int[][] setColumnToZero(int[][] matrix, int column) {
        for(int i = 0; i < matrix.length; i++) {
            matrix[i][column] = 0;
        }
        return matrix;
    }
    private static void printMatrix(int[][] matrix) {
        for(int i = 0; i < matrix.length; i++) {
            int j;
            StringBuilder stringBuilder = new StringBuilder("Row ");
            stringBuilder.append(i);
            stringBuilder.append(" : ");
            System.out.print(stringBuilder);
            for(j = 0; j < matrix[0].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
    public static int[][] zeroMatrixInPlace(int[][] matrix) {
        matrix = markElementsInPlace(matrix);
        for(int i = matrix[0].length - 1; i > 0; i--) {
            if(matrix[0][i] == 0 | matrix[0][i] == verticalMarker) {
                setColumnToZero(matrix, i);
            }

        }
        for(int i = matrix.length - 1; i > 0; i--) {
            if(matrix[i][0] == 0 | matrix[i][0] == horizontalMarker) {
                setRowToZero(matrix, i);
            }
        }
        if(matrix[0][0] == 0) {
            setRowToZero(matrix, 0);
            setColumnToZero(matrix, 0);
        } else if(isFirstElementSetToHorizontal(matrix)) {
            setRowToZero(matrix, 0);
        } else if(isFirstElementSetToVertical(matrix)) {
            setColumnToZero(matrix, 0);
        }
        return matrix;
    }
    private static int[][] markElementsInPlace(int[][] matrix) {
        for(int row = 0; row < matrix.length; row++) {
            for(int column = 0; column < matrix[0].length; column++) {
                if(matrix[row][column] == 0) {
                    if(row == 0 && isFirstElementSetToVertical(matrix)) {
                        matrix[0][0] = 0;
                    } else if(column == 0 && isFirstElementSetToHorizontal(matrix)) {
                        matrix[0][0] = 0;
                    }
                    if(!isTopMarkerZero(matrix, column)) {
                        matrix[0][column] = verticalMarker;
                    }
                    if(!isSideMarkerZero(matrix, row)) {
                        matrix[row][0] = horizontalMarker;
                    }
                }
            }
        }
        return matrix;
    }
    private static boolean isFirstElementSetToVertical(int[][] matrix) {
        return matrix[0][0] == verticalMarker;
    }
    private static boolean isFirstElementSetToHorizontal(int[][] matrix) {
        return matrix[0][0] == horizontalMarker;
    }
    private static boolean isTopMarkerZero(int[][] matrix, int column) {
        return matrix[0][column] == 0;
    }
    private static boolean isSideMarkerZero(int[][] matrix, int row) {
        return matrix[row][0] == 0;
    }
}