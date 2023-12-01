package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/** 
 * 	ExpenseTrackerModel represents the model of the application
 * 	and is the model component of the Model View Controller
 *  (MVC) architecture being used for this application.
 */ 
public class ExpenseTrackerModel {

  //encapsulation - data integrity
  private List<Transaction> transactions;
  private List<Integer> matchedFilterIndices;
  private List<ExpenseTrackerModelListener> listeners;

  // This is applying the Observer design pattern.                          
  // Specifically, this is the Observable class. 
    
  public ExpenseTrackerModel() {
    transactions = new ArrayList<Transaction>();
    matchedFilterIndices = new ArrayList<Integer>();
    listeners = new ArrayList<ExpenseTrackerModelListener>();
  }

  public void addTransaction(Transaction t) {
    // Perform input validation to guarantee that all transactions added are non-null.
    if (t == null) {
      throw new IllegalArgumentException("The new transaction must be non-null.");
    }
    transactions.add(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
  }

  public void removeTransaction(Transaction t) {
    transactions.remove(t);
    // The previous filter is no longer valid.
    matchedFilterIndices.clear();
  }

  public List<Transaction> getTransactions() {
    //encapsulation - data integrity
    return Collections.unmodifiableList(new ArrayList<>(transactions));
  }

  public void setMatchedFilterIndices(List<Integer> newMatchedFilterIndices) {
      // Perform input validation
      if (newMatchedFilterIndices == null) {
	  throw new IllegalArgumentException("The matched filter indices list must be non-null.");
      }
      for (Integer matchedFilterIndex : newMatchedFilterIndices) {
	  if ((matchedFilterIndex < 0) || (matchedFilterIndex > this.transactions.size() - 1)) {
	      throw new IllegalArgumentException("Each matched filter index must be between 0 (inclusive) and the number of transactions (exclusive).");
	  }
      }
      // For encapsulation, copy in the input list 
      this.matchedFilterIndices.clear();
      this.matchedFilterIndices.addAll(newMatchedFilterIndices);
  }

  public List<Integer> getMatchedFilterIndices() {
      // For encapsulation, copy out the output list
      List<Integer> copyOfMatchedFilterIndices = new ArrayList<Integer>();
      copyOfMatchedFilterIndices.addAll(this.matchedFilterIndices);
      return copyOfMatchedFilterIndices;
  }

  /**
   * Registers the given ExpenseTrackerModelListener for
   * state change events.
   *
   * @param listener The ExpenseTrackerModelListener to be registered
   * @return If the listener is non-null and not already registered,
   *         returns true. If not, returns false.
   */   
  public boolean register(ExpenseTrackerModelListener listener) {
      // For the Observable class, this is one of the methods.
      if (listener != null && !this.listeners.contains(listener)) {
    	  this.listeners.add(listener);
    	  return true;
      }
	  
      return false;
  }
  
  /** 
   * Returns the number of listeners that are registered 
   * @param none 
   * @return If the listener is non-null, returns length of
   * listeners list. If not, returns 0. 
   */
  public int numberOfListeners() {
      // For testing, this is one of the methods.
      if (this.listeners != null) {
    	  return this.listeners.size();
      }
      
      return 0;
  }

  /** 
   * Determines whether or not the listener is in list registered
   * @param listener The ExpenseTrackerModelListener
   * @return If the listener is in the list of registered listeners,
   * 	     returns true. If not, returns false. 
   */
  public boolean containsListener(ExpenseTrackerModelListener listener) {
      // For testing, this is one of the methods.  
      return this.listeners.contains(listener);
  }

  /** 
   * updates the view
   * @param none
   * @return Goes through each listener and updates
   * 		 using model.
   */  
  // Note: May need to change back to protected
  public void stateChanged() {
      // For the Observable class, this is one of the methods.
      for (ExpenseTrackerModelListener listener : this.listeners) {
    	  listener.update(this);
      }
  }
}
