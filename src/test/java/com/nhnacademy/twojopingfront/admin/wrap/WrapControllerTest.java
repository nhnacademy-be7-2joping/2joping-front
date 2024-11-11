package com.nhnacademy.twojopingfront.admin.wrap;

import com.nhnacademy.twojopingfront.admin.wrap.controller.WrapController;
import com.nhnacademy.twojopingfront.admin.wrap.dto.WrapRequestDto;
import com.nhnacademy.twojopingfront.admin.wrap.dto.WrapResponseDto;
import com.nhnacademy.twojopingfront.admin.wrap.service.WrapService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@WebMvcTest(WrapController.class)
class WrapControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WrapService wrapService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 기본 /admin/wraps 페이지가 잘 반환되는지 확인합니다.
     */
    @Test
    void testWrap() throws Exception {
        mockMvc.perform(get("/admin/wraps"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/wrap/wrap"));
    }

    /**
     * 포장 정책을 생성한 후 리디렉션이 정상적으로 이루어지는지 확인합니다.
     */
    @Test
    void testCreateWrap() throws Exception {
        WrapRequestDto requestDto = new WrapRequestDto("포장 정책1", 1000, true);
        mockMvc.perform(post("/admin/wraps")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", requestDto.name())
                        .param("wrapPrice", String.valueOf(requestDto.wrapPrice()))
                        .param("isActive", String.valueOf(requestDto.isActive())))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/wraps"));
    }

    /**
     * 특정 포장 정책을 조회하는 기능을 테스트합니다.
     */
    @Test
    void testGetWrap() throws Exception {
        WrapResponseDto responseDto = new WrapResponseDto(1L, "포장 정책1", 1000, true);
        when(wrapService.getWrap(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/admin/wraps/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/wrap/wrap-details"))
                .andExpect(model().attribute("wrap", responseDto));
    }

    /**
     * 전체 포장 정책 목록을 조회하는 기능을 테스트합니다.
     */
    @Test
    void testGetAllWraps() throws Exception {
        List<WrapResponseDto> wraps = List.of(
                new WrapResponseDto(1L, "포장 정책1", 1000, true),
                new WrapResponseDto(2L, "포장 정책2", 2000, false)
        );
        when(wrapService.getAllWraps()).thenReturn(wraps);

        mockMvc.perform(get("/admin/wraps/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/wrap/wrap-list"))
                .andExpect(model().attribute("wraps", wraps));
    }

    /**
     * 수정 화면 표시 기능을 테스트합니다.
     */
    @Test
    void testShowEditForm() throws Exception {
        WrapResponseDto responseDto = new WrapResponseDto(1L, "포장 정책1", 1000, true);
        when(wrapService.getWrap(1L)).thenReturn(responseDto);

        mockMvc.perform(get("/admin/wraps/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/wrap/wrap-edit"))
                .andExpect(model().attribute("wrap", responseDto));
    }

    /**
     * 포장 정책을 업데이트한 후 리디렉션이 정상적으로 이루어지는지 확인합니다.
     */
    @Test
    void testUpdateWrap() throws Exception {
        mockMvc.perform(put("/admin/wraps/1")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("name", "포장 정책 수정")
                        .param("wrapPrice", "1500")
                        .param("isActive", "true"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/wraps/list"));
    }

    /**
     * 포장 정책을 삭제한 후 리디렉션이 정상적으로 이루어지는지 확인합니다.
     */
    @Test
    void testDeleteWrap() throws Exception {
        mockMvc.perform(delete("/admin/wraps/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/wraps/list"));
    }
}