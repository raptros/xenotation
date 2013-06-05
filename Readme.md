# Xenotation

this is a scheme for encoding natural numbers. it is based on primes and prime factorizations (sort like hank hill but primes instead of propane).
yes I did stay up too late last night working on this.

## wat
look. the first prime is 2, so in this scheme, it is represented by 0. then there's this braket operation, which wraps some number n, and gets the n'th prime. e.g:
* 0 is 2
* (0) is 3 because 3 is the second prime (yes i know, i know, braket isn't zero indexed. the notation isn't my fault.)
* 00 is 4 because it's 2*2
* ((0)) is 5 because that's 3 third prime
* (0)0 is 6 because 3*2
* (00) is 7 because it's the 4th prime

so the operations are 0, braket, and sequence

## how to into use
you're on your own. get SBT installed and play around in the console.
