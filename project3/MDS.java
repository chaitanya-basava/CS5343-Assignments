/** Starter code for P3
 *  @author
 */

// Change to your net id
package sxb220302;

import java.util.*;

// If you want to create additional classes, place them in this file as subclasses of MDS

public class MDS {
    private class Object {
        int id;
        int price;
        java.util.List<Integer> description;
        Object(int id, int price, java.util.List<Integer> list) {
            this.id = id;
            this.price = price;
            this.description = list;
        }
    }

    // Add fields of MDS here
    private final Map<Integer, Object> objects;
    private final Map<Integer, Set<Integer>> descriptionMap;

    // Constructors
    public MDS() {
        objects = new HashMap<>();
        descriptionMap = new HashMap<>();
    }

    private void updateDescriptionMap(int id, java.util.List<Integer> list) {
        for(int i: list) {
            if(descriptionMap.containsKey(i)) {
                descriptionMap.get(i).add(id);
            } else {
                Set<Integer> set = new HashSet<>();
                set.add(id);
                descriptionMap.put(i, set);
            }
        }
    }

    private void removeFromDescriptionMap(int id, java.util.List<Integer> list) {
        for(int i: list) {
            if(descriptionMap.containsKey(i)) {
                descriptionMap.get(i).remove(id);
            }
        }
    }

    /* Public methods of MDS. Do not change their signatures.
       __________________________________________________________________
       a. Insert(id,price,list): insert a new item whose description is given
       in the list.  If an entry with the same id already exists, then its
       description and price are replaced by the new values, unless list
       is null or empty, in which case, just the price is updated. 
       Returns 1 if the item is new, and 0 otherwise.
    */
    public int insert(int id, int price, java.util.List<Integer> list) {
        int val = 1;
        Object obj = new Object(id, price, new LinkedList<>(list));

        if(objects.containsKey(id)) {
            val = 0;
            obj = objects.get(id);
            obj.price = price;
            if(list != null && !list.isEmpty()) {
                this.removeFromDescriptionMap(id, obj.description);
                obj.description = new LinkedList<>(list);
            }
        }
        objects.put(id, obj);
        this.updateDescriptionMap(id, obj.description);
	    return val;
    }

    // b. Find(id): return price of item with given id (or 0, if not found).
    public int find(int id) {
        if(objects.containsKey(id)) return objects.get(id).price;
	    return 0;
    }

    /* 
       c. Delete(id): delete item from storage.  Returns the sum of the
       ints that are in the description of the item deleted,
       or 0, if such an id did not exist.
    */
    public int delete(int id) {
        if(objects.containsKey(id)) {
            Object obj = objects.get(id);
            int sum = 0;
            for(int i: obj.description) sum += i;
            objects.remove(id);
            this.removeFromDescriptionMap(id, obj.description);
            return sum;
        }
	    return 0;
    }

    /* 
       d. FindMinPrice(n): given an integer, find items whose description
       contains that number (exact match with one of the ints in the
       item's description), and return lowest price of those items.
       Return 0 if there is no such item.
    */
    public int findMinPrice(int n) {
        if(descriptionMap.containsKey(n)) {
            int min = Integer.MAX_VALUE;
            for(int i: descriptionMap.get(n)) min = Math.min(objects.get(i).price, min);
            return min;
        }
	    return 0;
    }

    /* 
       e. FindMaxPrice(n): given an integer, find items whose description
       contains that number, and return highest price of those items.
       Return 0 if there is no such item.
    */
    public int findMaxPrice(int n) {
        if(descriptionMap.containsKey(n)) {
            int max = Integer.MIN_VALUE;
            for(int i: descriptionMap.get(n)) max = Math.max(objects.get(i).price, max);
            return max;
        }
	    return 0;
    }

    /* 
       f. FindPriceRange(n,low,high): given int n, find the number
       of items whose description contains n, and in addition,
       their prices fall within the given range, [low, high].
    */
    public int findPriceRange(int n, int low, int high) {
        int count = 0;
        if(descriptionMap.containsKey(n)) {
            for(int i: descriptionMap.get(n)) {
                if(objects.get(i).price >= low && objects.get(i).price <= high) count++;
            }
            return count;
        }
	    return count;
    }

    /*
      g. RemoveNames(id, list): Remove elements of list from the description of id.
      It is possible that some of the items in the list are not in the
      id's description.  Return the sum of the numbers that are actually
      deleted from the description of id.  Return 0 if there is no such id.
    */
    public int removeNames(int id, java.util.List<Integer> list) {
        int sum = 0;
        if(objects.containsKey(id)) {
            Object obj = objects.get(id);
            for(int i: list) {
                if(obj.description.contains(i)) {
                    sum += i;
                    obj.description.remove(Integer.valueOf(i));
                    this.removeFromDescriptionMap(id, Collections.singletonList(i));
                }
            }
            objects.put(id, obj);
        }
	    return sum;
    }
}

