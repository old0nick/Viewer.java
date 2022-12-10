public class Matrix {
    double[][] theMatrix;
    int row, col;

    public Matrix(int initRow, int initCol) { // Конструктор матрицы
        row = initRow;
        col = initCol;
        theMatrix = new double[row][col];
    }

    public Matrix multWithMatrix(Matrix matrix) {
        Matrix newMatrix = new Matrix(this.row, matrix.col);
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < matrix.col; j++) {
                int matRow = 0;
                int thisCol = 0;
                newMatrix.theMatrix[i][j] = 0;
                do {
                    newMatrix.theMatrix[i][j] += this.theMatrix[i][thisCol] * matrix.theMatrix[matRow][j];
                    thisCol++;
                    matRow++;
                    } while (matRow < matrix.row && thisCol < this.col);
            }
        }
        return newMatrix;
    }


}