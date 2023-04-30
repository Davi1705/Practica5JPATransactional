package com.example.jpa;

import com.example.jpa.model.Cliente;
import com.example.jpa.util.JpaUtil;
import jakarta.persistence.EntityManager;

import javax.swing.*;

public class HibernateEditar {
    public static void main(String[] args) {
        // Se solicita el id del cliente a actualizar
        Long id = Long.parseLong(JOptionPane.showInputDialog("Ingrese el id del cliente a actualizar:"));

        EntityManager em = JpaUtil.getEntityManager();

        try {
            // Se busca el cliente a actualizar en la base de datos
            Cliente cliente = em.find(Cliente.class, id);
            if (cliente == null) {
                JOptionPane.showMessageDialog(null, "No se encontró un cliente con el id ingresado");
                return;
            }

            // Se solicitan los nuevos valores de los atributos del cliente
            String nombre = JOptionPane.showInputDialog("Ingrese el nuevo nombre:", cliente.getNombre());
            String apellido = JOptionPane.showInputDialog("Ingrese el nuevo apellido:", cliente.getApellido());
            String formaPago = JOptionPane.showInputDialog("Ingrese la nueva forma de pago:", cliente.getFormaPago());

            // Se actualiza el cliente con los nuevos valores
            em.getTransaction().begin();
            cliente.setNombre(nombre);
            cliente.setApellido(apellido);
            cliente.setFormaPago(formaPago);
            em.getTransaction().commit();

            JOptionPane.showMessageDialog(null, "El cliente se actualizó correctamente");

        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocurrió un error al actualizar el cliente: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
