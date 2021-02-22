import java.awt.image.BufferedImage;

public class Metodos {

	
	
	/*
	 * Convertir la imagen de RGB a una matriz de grises de 0 a 255
	 * @author: Carlos Guillén Moreno
	 * @param: se trata de la imagen {@code BufferedImage} que cargaremos para aplicarle el filtro
	 * @return: devolvemos la imagen con escala de grises {code FingerPrintImage}
	 */
	FingerPrintImage metodo1(BufferedImage imagenEntrada) {
		// Clase definida para almacenar la huella en grises o ByN
		FingerPrintImage imagensalida = new FingerPrintImage(imagenEntrada.getWidth(), imagenEntrada.getHeight());
		int rgb=0;
		int r=0;
		int g=0;
		int b=0;
		int nivelGris=0;
		for (int x = 0; x < imagenEntrada.getWidth(); ++x) {
			for (int y = 0; y < imagenEntrada.getHeight(); ++y) {
				rgb = imagenEntrada.getRGB(x, y);
				r = (rgb >> 16) & 0xFF;
				g = (rgb >> 8) & 0xFF;
				b = (rgb & 0xFF);
				nivelGris = (r + g + b) / 3;
				imagensalida.setPixel(x, y, nivelGris);
			}
		}

		return imagensalida;
	}

	
	
	/*
	 * Convierte la clase huella (FingerPrintImage) a una imagen
	 * @author: Carlos Guillén Moreno
	 * @param: huella generada  {@code FingerPrintImage} en otro método
	 * @param: modo {@code int} en el que generar la imagen, solo puede tomar los valores 0 y 1
	 * @return: una imagen {@code BufferedImage}
	 * 
	 */
	BufferedImage metodo2(FingerPrintImage imagenEntrada, int modo) {
		BufferedImage imagenSalida = new BufferedImage(imagenEntrada.getAncho(),imagenEntrada.getAlto(),1);

		for (int x = 0; x < imagenEntrada.getAncho(); ++x) {
			for (int y = 0; y < imagenEntrada.getAlto(); ++y) {
				int valor = imagenEntrada.getPixel(x, y);
				if (modo == 0) {
					valor = valor * 255;
				}
				int pixelRGB = (255 << 24 | valor << 16 | valor << 8 | valor);
				imagenSalida.setRGB(x, y, pixelRGB);
			}
		}
		
		return imagenSalida;
		
	}
	
	//Realizar el histograma de la imagen en escala de grises
	/*
	 * @author: Carlos Guillén Moreno
	 * @param: huella generada en escala de grises {@code FingerPrintImage}
	 * @return: devolvemos la imagen ecualizada {code FingerPrintImage}
	 */
	FingerPrintImage metodo3(FingerPrintImage imagenEntrada) {

		int width = imagenEntrada.getAncho();
		int height = imagenEntrada.getAlto();
		FingerPrintImage imagenEcualizada = new FingerPrintImage(width, height);
		int tampixel = width * height;
		int[] histograma = new int[256];

		// Calculamos frecuencia relativa de ocurrencia
		// de los distintos niveles de gris en la imagen
		for (int x = 0; x < width ; x++) {
			for (int y = 0; y < height; y++) {
				int valor = imagenEntrada.getPixel(x, y);
				histograma[valor]++;
			}
		}
		int sum = 0;
		// Construimos la Lookup table LUT
		float[] lut = new float[256];
		for (int i = 0; i < 256; ++i) {
			sum += histograma[i];
			lut[i] = sum * 255 / tampixel;
		}
		// Se transforma la imagen utilizando la tabla LUT
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int valor = imagenEntrada.getPixel(x, y);
				int valorNuevo = (int) lut[valor];
				imagenEcualizada.setPixel(x, y, valorNuevo);
			}
		}
		return imagenEcualizada;
	}
	
	
	//Convertir la imagen de grises a una matriz de Blanco y Negro
	/* @author: Carlos Guillén Moreno
	 *  @param: huella generada en escala de grises {@code FingerPrintImage}
	 *  @param: {@code int} este valor determinará el umbral a partir del cual tomará el pixel el valor 1 (negro) si lo supera y blanco en cualquier otro caso (menor o igual)
	 *  @return: devolvemos la imagen en blanco y negro {code FingerPrintImage}
	 */
	FingerPrintImage metodo4(FingerPrintImage imagenEntrada, int umbral) {
		FingerPrintImage imagenSalida = new FingerPrintImage(imagenEntrada.getAncho(), imagenEntrada.getAlto());
		for (int x = 0; x < imagenEntrada.getAncho(); ++x) {
			for (int y = 0; y < imagenEntrada.getAlto(); ++y) {
				int valor = imagenEntrada.getPixel(x, y);
				if (valor < umbral) {
					//Negro
					imagenSalida.setPixel(x, y, 0);
				} else {
					//Blanco
					imagenSalida.setPixel(x, y, 1);
				}
			}
		}
		return imagenSalida;
	}
	
	
	
	

	/*
	 * Eliminación del ruido binario. Rellena los pequeños huecos de un pixel en zonas oscuras y los cortes y muescas en segmentos de lados rectos.
	 * @author: Carlos Guillén Moreno
	 * @param: huella generada en escala de grises {@code FingerPrintImage}
	 * @return: devolvemos la imagen con el filtro aplicado {code FingerPrintImage}
	 */
	FingerPrintImage filtroBinario1(FingerPrintImage imagenEntrada) {
		FingerPrintImage imagenSalida = new FingerPrintImage(imagenEntrada.getAncho(), imagenEntrada.getAlto());
		int B1, a, b, c, d, p, e, f, g, h;
		
		//Para que no salgan bordes
		for (int i = 0; i < imagenEntrada.getAncho(); i++) {
			for (int j = 0; j < imagenEntrada.getAlto(); j++) {
				 imagenSalida.setPixel(i, j, 1);
			}
		}
		
		
		//Hasta -1 para que no se salga de los limites de la matriz
		//Empiezo en 1 por el mismo motivo, no salirme de los limites
		for (int i = 1; i < imagenEntrada.getAncho()-1; i++) {
			for (int j = 1; j < imagenEntrada.getAlto()-1; j++) {
				    a = imagenEntrada.getPixel(i-1, j-1);
	                b = imagenEntrada.getPixel(i-1, j);
	                c = imagenEntrada.getPixel(i-1, j+1);
	                d = imagenEntrada.getPixel(i, j-1);
	                p = imagenEntrada.getPixel(i, j);
	                e = imagenEntrada.getPixel(i, j+1);
	                f = imagenEntrada.getPixel(i+1, j-1);
	                g = imagenEntrada.getPixel(i+1, j);
	                h = imagenEntrada.getPixel(i+1, j+1);
	                B1 = p | b & g & (d | e) | d & e & (b | g);
	                imagenSalida.setPixel(i, j, B1);
			}
			
		}
		
		return imagenSalida;
	}
	

	/*
	 * Eliminación del ruido binario. Elimina los unos aislados y las peque.as protuberancias a lo largo de segmentos de lados rectos
	 * @author: Carlos Guillén Moreno
	 * @param: huella generada en escala de grises {@code FingerPrintImage}
	 * @return: devolvemos la imagen con el filtro aplicado {code FingerPrintImage}
	 */
	FingerPrintImage filtroBinario2(FingerPrintImage imagenEntrada) {
		FingerPrintImage imagenSalida = new FingerPrintImage(imagenEntrada.getAncho(), imagenEntrada.getAlto());
		int B2, a, b, c, d, p, e, f, g, h;

		// Para que no salgan bordes negros
		for (int i = 0; i < imagenEntrada.getAncho(); i++) {
			for (int j = 0; j < imagenEntrada.getAlto(); j++) {
				imagenSalida.setPixel(i, j, 1);
			}
		}

		// Hasta -1 para que no se salga de los limites de la matriz
		// Empiezo en 1 por el mismo motivo, no salirme de los limites
		for (int i = 1; i < imagenEntrada.getAncho() - 1; i++) {
			for (int j = 1; j < imagenEntrada.getAlto() - 1; j++) {
				a = imagenEntrada.getPixel(i - 1, j - 1);
				b = imagenEntrada.getPixel(i - 1, j);
				c = imagenEntrada.getPixel(i - 1, j + 1);
				d = imagenEntrada.getPixel(i, j - 1);
				p = imagenEntrada.getPixel(i, j);
				e = imagenEntrada.getPixel(i, j + 1);
				f = imagenEntrada.getPixel(i + 1, j - 1);
				g = imagenEntrada.getPixel(i + 1, j);
				h = imagenEntrada.getPixel(i + 1, j + 1);
				B2 = p & ((a | b | d) & (e | g | h) | (b | c | e) & (d | f | g));
				imagenSalida.setPixel(i, j, B2);
			}

		}

		return imagenSalida;
	}
	
	
	
	
	
	
	
	

}
