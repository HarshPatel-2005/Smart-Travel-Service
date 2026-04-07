// Explain why LinkedList vs ArrayList for this use case:

// For the recentList class, the reason why we use LinkedList over ArrayList is because LinkedList is a data structure that is useful to keep track of an ordered list. What this means is that 
// it is useful when it comes to knowing what came in, what's in ahead of it and what's preceding it. This is useful when it comes to printing an order from what came in first until what came in last. In our case,
// since we are printing the recent trips, what this means is that if the user has viewed a couple of trips, a linkedList object can save the order that it has entered and print out the latest object until the oldest.
// ArrayList on the other hand has the capabilities to do this but it is inefficient when it comes to adding items and removing items, whilst linkedList on the other hand is quicker.

// A2 Compatibility:

// Y - All Original Menus work Unchanged