
public class FingerPrintImage {
	int[][] matrizImagen;

	public int[][] getImage() {
		return matrizImagen;
	}

	public void setImage(int[][] image) {
		this.matrizImagen = image;
	}

	public FingerPrintImage(int Alto, int Ancho) {
		matrizImagen = new int[Alto][Ancho];
	}

	public void setPixel(int x, int y, int pixel) {
		matrizImagen[x][y] = pixel;
	}

	public int getPixel(int x, int y) {
		return matrizImagen[x][y];
	}
	//Cantidad de elementos de una fila, ancho
	public int getAncho() {
		
		return matrizImagen.length;
	}
	//Cantidad de filas de la matriz, altura
	public int getAlto() {
		return matrizImagen[0].length;
	}
}
