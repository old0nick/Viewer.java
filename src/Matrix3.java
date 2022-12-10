public class Matrix3 {
    double[] values;
    Matrix3 (double[] values){
        this.values = values;
    }

    Matrix3 multiply(Matrix3 m2){
        double[] result = new double[9];
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                for (int i = 0; i < 3; i++) {
                    result[row * 3 + col] += this.values[row * 3 + i] * m2.values[i * 3 + col];
                }
            }
        }
        return new Matrix3(result);
    }

    Vertex transform(Vertex v){
        return new Vertex(v.x * values[0] + v.y * values[3] + v.z * values[6],
                v.x * values[1] + v.y * values[4] + v.z * values[7],
                v.x * values[2] + v.y * values[5] + v.z * values[8]);
    }

}
