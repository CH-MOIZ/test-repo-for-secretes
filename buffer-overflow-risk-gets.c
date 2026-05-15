#include <stdio.h>

int DST_BUFFER_SIZE = 120;

int bad_code() {
    char str[DST_BUFFER_SIZE];
    // ruleid:buffer-overflow-risk-gets
    gets(str);
    printf("%s", str);
    return 0;
}

int main() {
    char str[DST_BUFFER_SIZE];
    // ok:buffer-overflow-risk-gets
    fgets(str);
    printf("%s", str);
    return 0;
}
