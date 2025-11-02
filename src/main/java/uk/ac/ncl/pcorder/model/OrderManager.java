package uk.ac.ncl.pcorder.model;

import java.util.*;

/**
 * Manages all customer orders for the PC ordering system.
 * <p>
 * This class stores every placed order, allows new ones to be added,
 * and supports simple operations such as cancelling or fulfilling orders.
 * Later, it will also provide summary information (largest customer, etc.).
 * </p>
 */
public class OrderManager {

    /** Internal list that keeps a record of all orders. */
    private final List<Order> orders;

    /** Creates an empty OrderManager with no existing history. */
    public OrderManager() {
        orders = new ArrayList<>();
    }

    /**
     * @return an unmodifiable list of all orders currently stored.
     * Used mainly for viewing or testing.
     */
    public List<Order> getAllOrders() {
        return Collections.unmodifiableList(orders);
    }

    /**
     * Adds an existing order object into the manager.
     * (Mainly used for testing or manual insertion.)
     *
     * @param order the order to add
     */
    public void addOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        orders.add(order);
    }

    /** Removes all stored orders. Useful for resetting during tests. */
    public void clearAll() {
        orders.clear();
    }

    /**
     * Places a new order for the given customer and PC model.
     * <p>
     * An order can be placed only if the credit card is valid.
     * </p>
     *
     * @param model    the PC model being ordered
     * @param customer the customer placing the order
     * @param card     the credit card used for payment
     * @return the newly created Order object
     */
    public Order placeOrder(Customer customer, PCModel model, CreditCard card) {
        if (model == null || customer == null || card == null) {
            throw new IllegalArgumentException("Model, customer, and card must not be null");
        }

        if (!card.isValid()) {
            throw new IllegalArgumentException("Credit card is not valid");
        }

        Order newOrder = new Order(customer, model, card);
        orders.add(newOrder);
        return newOrder;
    }

    /**
     * Cancels a given order if it has not yet been fulfilled.
     *
     * @param order the order to cancel
     * @return true if cancellation succeeded, false otherwise
     */
    public boolean cancelOrder(Order order) {
        if (order == null) return false;
        if (order.getStatus() == OrderStatus.PLACED) {
            order.cancelOrder();
            return true;
        }
        return false;
    }

    /**
     * Marks an order as fulfilled if possible.
     * Fulfilled orders cannot later be cancelled.
     *
     * @param order the order to fulfil
     * @return true if fulfilment succeeded, false otherwise
     */
    public boolean fulfilOrder(Order order) {
        if (order == null) return false;
        if (order.getStatus() == OrderStatus.PLACED) {
            order.fulfilOrder();
            return true;
        }
        return false;
    }
    /**
     * Finds the customer with the most fulfilled orders.
     * If there's a tie, returns the one whose name comes first alphabetically.
     *
     * @return a Map.Entry with the customer and their fulfilled order count,
     * or null if there are no fulfilled orders.
     */
    public Map.Entry<Customer, Integer> getLargestCustomer() {
        if (orders.isEmpty()) return null;

        Map<Customer, Integer> fulfilledCount = new HashMap<>();

        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.FULFILLED) {
                fulfilledCount.merge(order.getCustomer(), 1, Integer::sum);
            }
        }

        if (fulfilledCount.isEmpty()) return null;

        return fulfilledCount.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = b.getValue().compareTo(a.getValue());
                    if (cmp == 0) {
                        return a.getKey().toString().compareToIgnoreCase(b.getKey().toString());
                    }
                    return cmp;
                })
                .findFirst()
                .orElse(null);
    }

    /**
     * Finds the most ordered preset model (manufacturer + model name).
     * Uses alphabetical order to break ties.
     *
     * @return a Map.Entry with model name and count, or null if none fulfilled.
     */
    public Map.Entry<String, Integer> getMostOrderedModel() {
        if (orders.isEmpty()) return null;

        Map<String, Integer> modelCount = new HashMap<>();

        for (Order order : orders) {
            if (order.getStatus() == OrderStatus.FULFILLED && order.getModel() instanceof PresetPCModel) {
                PresetPCModel preset = (PresetPCModel) order.getModel();
                String key = preset.getManufacturer() + " - " + preset.getName();
                modelCount.merge(key, 1, Integer::sum);
            }
        }

        if (modelCount.isEmpty()) return null;

        return modelCount.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = b.getValue().compareTo(a.getValue());
                    if (cmp == 0) {
                        return a.getKey().compareToIgnoreCase(b.getKey());
                    }
                    return cmp;
                })
                .findFirst()
                .orElse(null);
    }
    /**
     * Finds the most ordered part across all fulfilled custom models.
     * <p>
     * If two parts have been ordered the same number of times,
     * the one that comes first alphabetically is returned.
     * </p>
     *
     * @return a Map.Entry with the part name and how many times it was ordered,
     * or null if no custom models were fulfilled.
     */
    public Map.Entry<String, Integer> getMostOrderedPart() {
        if (orders.isEmpty()) return null;

        Map<String, Integer> partCount = new HashMap<>();

        for (Order order : orders) {
            // We only look at fulfilled custom models
            if (order.getStatus() == OrderStatus.FULFILLED && order.getModel() instanceof CustomPCModel) {
                CustomPCModel custom = (CustomPCModel) order.getModel();

                for (String part : custom.getParts()) {
                    partCount.merge(part, 1, Integer::sum);
                }
            }
        }

        if (partCount.isEmpty()) return null;

        return partCount.entrySet().stream()
                .sorted((a, b) -> {
                    int cmp = b.getValue().compareTo(a.getValue());
                    if (cmp == 0) {
                        return a.getKey().compareToIgnoreCase(b.getKey());
                    }
                    return cmp;
                })
                .findFirst()
                .orElse(null);
    }
}
