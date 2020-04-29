# python3
import sys


def SortCharacters(S):
    order = [0] * len(S)
    char_set = sorted(set(S))
    count = [S.count(c) for c in char_set]
    for i in range(1,len(char_set)):
        count[i]=count[i]+count[i-1]
    for i in range(len(order)-1,-1,-1):
        char = S[i]
        count[char_set.index(char)]=count[char_set.index(char)]-1
        order[count[char_set.index(char)]] = i
    return order
def ComputeCharClasses(S, order):
    n = len(S)
    classes = [0 for _ in range(n)]
    classes[order[0]] = 0
    for i in range(1,len(S)):
        if S[order[i]]!=S[order[i-1]]:
            classes[order[i]] = classes[order[i - 1]] + 1
        else:
            classes[order[i]]=classes[order[i-1]]
    return classes
def SortDoubled(S, L, order, classes):
    n = len(S)
    count = [0 for _ in range(n)]
    newOrder = [0 for _ in range(n)]
    for i in range(n):
        count[classes[i]]=count[classes[i]]+1
    for i in range(1,n):
        count[i]=count[i]+count[i-1]
    for i in range(n-1,-1,-1):
        start = (order[i]-L+n)%n
        cl = classes[start]
        count[cl]=count[cl]-1
        newOrder[count[cl]]=start
    return newOrder
def UpdateClasses(newOrder, classes, L):
    n = len(newOrder)
    newClass=[0 for _ in range(n)]
    newClass[newOrder[0]] = 0
    for i in range(1,n):
        cur=newOrder[i]
        prev =newOrder[i - 1]
        mid =(cur + L)
        midPrev = (prev + L)% n
        if classes[cur]!= classes[prev] or classes[mid]!= classes[midPrev]:
            newClass[cur] =newClass[prev] + 1
        else:
            newClass[cur] =newClass[prev]
    return newClass


def build_suffix_array(S):
    """
    Build suffix array of the string text and
    return a list result of the same length as the text
    such that the value result[i] is the index (0-based)
    in text where the i-th lexicographically smallest
    suffix of text starts.
    """
    order = SortCharacters(S)
    classes = ComputeCharClasses(S, order)
    L=1
    n = len(S)
    while L<n:
        order = SortDoubled(S, L, order, classes)
        classes = UpdateClasses(order, classes, L)
        L=2*L
    # Implement this function yourself
    return order


if __name__ == '__main__':
  text = sys.stdin.readline().strip()
  print(" ".join(map(str, build_suffix_array(text))))