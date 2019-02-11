package com.jonathann.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.jonathann.model.Producto;

public class MainApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opcion = 0;
		Scanner sn = new Scanner(System.in);
		Producto p;

		EntityManager entity = JPAUtil.getEntityManagerFactory().createEntityManager();
		while (opcion != 5) {
			System.out.println("1. CREAR PRODUCTO");
			System.out.println("2. BUSCAR PRODUCTO");
			System.out.println("3. ACTUALIZAR PRODUCTO");
			System.out.println("4. ELIMINAR PRODUCTO");
			System.out.println("5. SALIR");

			opcion = sn.nextInt();

			switch (opcion) {
			case 1:
				System.out.println("Digite el nombre del producto");
				p = new Producto();
				sn.nextLine();
				p.setNombre(sn.nextLine());
				System.out.println("Digite el precio del producto");
				p.setPrecio(sn.nextDouble());
				System.out.println(p);
				
				entity.getTransaction().begin();
				entity.persist(p);
				entity.getTransaction().commit();
				
				System.out.println("Producto Registrado");
				System.out.println();
				
				break;
			case 2:
				System.out.println("Digite el id del producto a buscar");
				p = new Producto();
				p = entity.find(Producto.class, sn.nextLong());
				if(p != null){
					System.out.println(p);
					System.out.println();
				} else{
					System.out.println();
					System.out.println("Producto no encontrado....Lista de productos completa");
					List<Producto>listaProductos = new ArrayList<Producto>();
					 Query query = entity.createQuery("Select p FROM Producto p");
					 listaProductos = query.getResultList();
					 for (Producto producto : listaProductos) {
						System.out.println(producto);
					}
					 System.out.println();
				}

				break;
			case 3:
				System.out.println("Digite el id del producto a actualizar");
				p = new Producto();
				p = entity.find(Producto.class,sn.nextLong());
				if(p != null){
					System.out.println(p);
					System.out.println("Digite el nombre del producto");
					sn.nextLine();
					p.setNombre(sn.nextLine());
					System.out.println("Digite el precio del producto");
					p.setPrecio(sn.nextDouble());
					
					entity.getTransaction().begin();
					entity.merge(p);
					entity.getTransaction().commit();
					
					System.out.println("Producto Actualizado...");
					System.out.println();
				}
				break;
			case 4:
				System.out.println("Digite el id del producto a eliminar.");
				p = new Producto();
				p = entity.find(Producto.class,sn.nextLong());
				if(p != null){
					System.out.println(p);
					entity.getTransaction().begin();
					entity.remove(p);
					entity.getTransaction().commit();
					
					System.out.println("Producto eliminado");
				}else{
					System.out.println("Producto No encontrado");
				}

				break;
			case 5:
				entity.close();
				JPAUtil.shutdown();

				break;

			default:
				break;
			}
		}
	}

}
