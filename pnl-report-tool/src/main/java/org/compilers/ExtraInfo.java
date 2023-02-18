package org.compilers;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Extra user-provided information.
 */
public class ExtraInfo {
  private Map<Long, List<ExtraInfoEntry>> entries = new TreeMap<>();

  /**
   * Add an entry to the info storage.
   *
   * @param infoEntry The info entry to add
   */
  public void add(ExtraInfoEntry infoEntry) {
    List<ExtraInfoEntry> entryList = entries.get(infoEntry.utcTimestamp());
    if (entryList == null) {
      entryList = new LinkedList<>();
      entries.put(infoEntry.utcTimestamp(), entryList);
    }
    entryList.add(infoEntry);
  }

  /**
   * Check if the extra info set is empty.
   *
   * @return True if no info is stored here, false otherwise
   */
  public boolean isEmpty() {
    return entries.isEmpty();
  }

  /**
   * Get all entries stored here.
   *
   * @return All the extra info entries, ordered by timestamp.
   */
  public List<ExtraInfoEntry> getAllEntries() {
    List<ExtraInfoEntry> allEntries = new LinkedList<>();
    for (List<ExtraInfoEntry> entryList : entries.values()) {
      allEntries.addAll(entryList);
    }
    return allEntries;
  }

  /**
   * Check if this information storage contains the provided entry.
   *
   * @param e The entry to check
   * @return True if this info storage contains the requested info entry, false otherwise
   */
  public boolean contains(ExtraInfoEntry e) {
    boolean found = false;
    Iterator<ExtraInfoEntry> it = getAllEntries().iterator();
    while (!found && it.hasNext()) {
      ExtraInfoEntry existingEntry = it.next();
      found = existingEntry.utcTimestamp() == e.utcTimestamp()
          && existingEntry.type().equals(e.type());
    }
    return found;
  }
}
