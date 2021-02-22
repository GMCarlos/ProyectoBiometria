import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class main {

	public static void main(String[] args) throws IOException {
		Metodos metodo = new Metodos();
		
		//Pasar a gris la imagen
		BufferedImage imagen = ImageIO.read(new File("huella.jpg"));
		FingerPrintImage finger = metodo.metodo1(imagen);
		File salida = new File("ImagenGris.jpg");
		BufferedImage aux = metodo.metodo2(finger, 1);
		ImageIO.write(aux, "jpg", salida);
		
		//Histograma (Ecualizado)
		FingerPrintImage finger2 = metodo.metodo3(finger);
		File salida2 = new File("Histograma.jpg");
		BufferedImage aux2 = metodo.metodo2(finger2, 1);
		ImageIO.write(aux2, "jpg", salida2);
		
		//Convertir de gris a Blanco/Negro
		FingerPrintImage finger3 = metodo.metodo4(finger2, 75);
		File salida3 = new File("BlancoNegro.jpg");
		BufferedImage aux3 = metodo.metodo2(finger3, 0);
		ImageIO.write(aux3, "jpg", salida3);
		
		//Filtro Binario1
		FingerPrintImage finger4 = metodo.filtroBinario1(finger3);
		File salida4 = new File("FiltroBinario1.jpg");
		BufferedImage aux4 = metodo.metodo2(finger4, 0);
		ImageIO.write(aux4, "jpg", salida4);
		
		//Filtro Binario2
		FingerPrintImage finger5 = metodo.filtroBinario2(finger4);
		File salida5 = new File("FiltroBinario2.jpg");
		BufferedImage aux5 = metodo.metodo2(finger5, 0);
		ImageIO.write(aux5, "jpg", salida5);
		
		
		
		
		
		
		
		
		
		
	}

}
